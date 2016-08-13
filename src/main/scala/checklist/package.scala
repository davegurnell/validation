// package io.underscore

// import scala.language.implicitConversions

// package object checklist extends Validators with ValidationResultImplicits with ValidationPathImplicits {
//   implicit class ValidatableOps[A](in: A) {
//     def validate(implicit validator: Validator[A]) =
//       validator(in).withValue(in)
//   }

//   implicit def functionToValidator[A](func: A => Seq[ValidationResult]): Validator[A] =
//     Validator[A] { in => func(in) }
// }
