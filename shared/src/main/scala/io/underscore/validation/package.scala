package io.underscore

import scala.language.implicitConversions

package object validation extends Validators with ValidationResultImplicits with ValidationPathImplicits {
  implicit class ValidatableOps[A : Validator](in: A) {
    def validate = implicitly[Validator[A]] apply in withValue in
  }

  implicit def functionToValidator[A](func: A => Seq[ValidationResult]): Validator[A] =
    Validator[A] { in => func(in) }
}
