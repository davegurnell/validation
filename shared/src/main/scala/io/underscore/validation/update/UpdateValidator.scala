package io.underscore.validation
package update

import scala.language.experimental.macros
import scala.language.higherKinds

trait UpdateValidator[A] extends ((A, A) => Seq[ValidationResult]) {
  def ~(that: UpdateValidator[A]): UpdateValidator[A] =
    this and that

  def and(that: UpdateValidator[A]): UpdateValidator[A] =
    UpdateValidator[A] { (updated, original) =>
      this(updated, original) ++ that(updated, original)
    }

  def contramap[B](func: B => A): UpdateValidator[B] =
    UpdateValidator[B] { (updated, original) =>
      this(func(updated), func(original))
    }

  def prefix[B: ValidationPathPrefix](prefixes: B *): UpdateValidator[A] =
    UpdateValidator[A] { (updated, original) =>
      this(updated, original).prefix(prefixes : _*)
    }

  def field[B](field: String, accessor: A => B)(implicit validator: UpdateValidator[B]): UpdateValidator[A] =
    this and (validator contramap accessor prefix field)

  def field[B](accessor: A => B)(implicit validator: UpdateValidator[B]): UpdateValidator[A] =
    macro ValidationMacros.fieldUpdate[A, B]

  def seqField[B](field: String, accessor: A => Seq[B])(implicit validator: UpdateValidator[B]): UpdateValidator[A] =
    this and UpdateValidator[A] { (updated, original) =>
      (accessor(updated) zip accessor(original)) flatMap validator.tupled prefix field
    }

  def seqField[B](accessor: A => Seq[B])(implicit validator: UpdateValidator[B]): UpdateValidator[A] =
    macro ValidationMacros.seqFieldUpdate[A, B]
}

object UpdateValidator {
  def apply[A](func: (A, A) => Seq[ValidationResult]) = new UpdateValidator[A] {
    def apply(updated: A, original: A) = func(updated, original)
  }
}
