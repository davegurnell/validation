package io.underscore.validation

import scala.language.experimental.macros
import scala.language.higherKinds

trait Validator[A] extends (A => Seq[ValidationResult]) {
  def ~(that: Validator[A]): Validator[A] =
    this and that

  def and(that: Validator[A]): Validator[A] =
    Validator[A] { in => this (in) ++ that(in) }

  def andPrefix[B](fields: String*)(that: Validator[A]): Validator[A] =
    this and (that.prefix(fields: _*))

  def seq: Validator[Seq[A]] =
    Validator[Seq[A]] { seq =>
      seq.zipWithIndex.foldLeft(pass) { (accum, pair) =>
        accum ++ (this (pair._1) prefix pair._2)
      }
    }

  def contramap[B](func: B => A): Validator[B] =
    Validator[B] { in => this (func(in)) }

  def prefix[B: ValidationPathPrefix](prefixes: B*): Validator[A] =
    Validator[A] { in => this (in) prefix (prefixes: _*) }

  def field[B](field: String, accessor: A => B)(implicit validator: Validator[B]): Validator[A] =
    this and (validator contramap accessor prefix field)

  def field[B](accessor: A => B)(implicit validator: Validator[B]): Validator[A] =
  macro ValidationMacros.field[A, B]

  def fieldWith[B](field: String, accessor: A => B)(implicit validatorBuilder: A => Validator[B]): Validator[A] =
    this and Validator[A] { value =>
      val validator = validatorBuilder(value) contramap accessor prefix field
      validator(value)
    }

  def fieldWith[B](accessor: A => B)(implicit validatorBuilder: A => Validator[B]): Validator[A] =
  macro ValidationMacros.fieldWith[A, B]

  def seqField[B](field: String, accessor: A => Seq[B])(implicit validator: Validator[B]): Validator[A] =
    this and (validator.seq contramap accessor prefix field)

  def seqField[B](accessor: A => Seq[B])(implicit validator: Validator[B]): Validator[A] =
  macro ValidationMacros.seqField[A, B]

  def seqFieldWith[B](field: String, accessor: A => Seq[B])(implicit validatorBuilder: A => Validator[B]): Validator[A] =
    this and Validator[A] { value =>
      val validator = validatorBuilder(value).seq contramap accessor prefix field
      validator(value)
    }

  def seqFieldWith[B](accessor: A => Seq[B])(implicit validatorBuilder: A => Validator[B]): Validator[A] =
  macro ValidationMacros.seqFieldWith[A, B]
}

object Validator {
  def apply[A](func: A => Seq[ValidationResult]) = new Validator[A] {
    def apply(in: A) = func(in)
  }
}
