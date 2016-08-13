package checklist.update

import cats._
import cats.data._
import cats.instances.all._
import cats.syntax.all._
import checklist.core._
import scala.language.higherKinds
import Message.{errors, warnings}

trait UpdateRule[A, B, C] {
  def apply(original: A, update: B): Checked[C]

  def tupled: Rule[(A, B), C] =
    Rule.pure(pair => this(pair._1, pair._2))

  def map[D](func: C => D): UpdateRule[A, B, D] =
    UpdateRule.map(this, func)

  def flatMap[D](func: C => UpdateRule[A, B, D]): UpdateRule[A, B, D] =
    UpdateRule.flatMap(this, func)

  def andThen[D](that: Rule[C, D]): UpdateRule[A, B, D] =
    UpdateRule.andThen(this, that)

  def zip[D](that: UpdateRule[A, B, D]): UpdateRule[A, B, (C, D)] =
    UpdateRule.zip(this, that)

  def and(that: UpdateRule[A, B, C]): UpdateRule[A, B, C] =
    UpdateRule.and(this, that)
}

object UpdateRule extends BaseUpdateRules
  with CombinatorUpdateRules

trait BaseUpdateRules {
  def pure[A, B, C](func: (A, B) => Checked[C]): UpdateRule[A, B, C] =
    new UpdateRule[A, B, C] {
      def apply(original: A, update: B) =
        func(original, update)
    }

  def alwaysReplace[A, B >: A]: UpdateRule[A, B, B] =
    pure[A, B, B]((original, update) => Ior.right(update))

  def alwaysIgnore[A, B >: A]: UpdateRule[A, B, A] =
    pure[A, B, A]((original, update) => Ior.right(original))

  def cannotChange[A, B >: A]: UpdateRule[A, B, B] =
    cannotChange(errors("Cannot be changed"))

  def cannotChange[A, B >: A](messages: Messages): UpdateRule[A, B, B] =
    pure[A, B, B] { (original, update) =>
      if(original == update) {
        Ior.right(update)
      } else {
        Ior.both(messages, update)
      }
    }

  def canChangeTo[A, B >: A](test: PartialFunction[B, Boolean]): UpdateRule[A, B, B] =
    canChangeTo(errors("Cannot be changed to that value"))(test)

  def canChangeTo[A, B >: A](messages: Messages)(test: PartialFunction[B, Boolean]): UpdateRule[A, B, B] =
    pure[A, B, B] { (original, update) =>
      if(original == update || (test.isDefinedAt(update) && test(update))) {
        Ior.right(update)
      } else {
        Ior.both(messages, update)
      }
    }
}

trait CombinatorUpdateRules {
  self: BaseUpdateRules =>

  def map[A, B, C, D](rule: UpdateRule[A, B, C], func: C => D): UpdateRule[A, B, D] =
    pure[A, B, D]((original, update) => rule(original, update).map(func))

  def flatMap[A, B, C, D](rule: UpdateRule[A, B, C], func: C => UpdateRule[A, B, D]): UpdateRule[A, B, D] =
    pure[A, B, D]((original, update) => rule(original, update).flatMap(ans => func(ans)(original, update)))

  def andThen[A, B, C, D](rule1: UpdateRule[A, B, C], rule2: Rule[C, D]): UpdateRule[A, B, D] =
    pure[A, B, D]((original, update) => rule1(original, update).flatMap(rule2.apply))

  def zip[A, B, C, D](rule1: UpdateRule[A, B, C], rule2: UpdateRule[A, B, D]): UpdateRule[A, B, (C, D)] =
    pure[A, B, (C, D)] { (original, update) =>
      rule1(original, update) match {
        case Ior.Left(msg1) =>
          rule2(original, update) match {
            case Ior.Left(msg2)    => Ior.left(msg1 concat msg2)
            case Ior.Right(c)      => Ior.left(msg1)
            case Ior.Both(msg2, c) => Ior.left(msg1 concat msg2)
          }
        case Ior.Right(b) =>
          rule2(original, update) match {
            case Ior.Left(msg2)    => Ior.left(msg2)
            case Ior.Right(c)      => Ior.right((b, c))
            case Ior.Both(msg2, c) => Ior.both(msg2, (b, c))
          }
        case Ior.Both(msg1, b) =>
          rule2(original, update) match {
            case Ior.Left(msg2)    => Ior.left(msg1 concat msg2)
            case Ior.Right(c)      => Ior.both(msg1, (b, c))
            case Ior.Both(msg2, c) => Ior.both(msg1 concat msg2, (b, c))
          }
      }
    }

  def and[A, B, C](rule1: UpdateRule[A, B, C], rule2: UpdateRule[A, B, C]): UpdateRule[A, B, C] =
    zip(rule1, rule2).map(_._1)
}