// package checklist

// import scala.language.implicitConversions

// package object update extends UpdateValidators {
//   implicit class UpdateValidatableOps[A](updated: A) {
//     def validateUpdateOf(original: A)(implicit validator: UpdateValidator[A]) =
//       validator(updated, original).withValue(updated)
//   }

//   implicit def functionToUpdateValidator[A](func: (A, A) => Seq[ValidationResult]): UpdateValidator[A] =
//     UpdateValidator[A] { (a, b) => func(a, b) }
// }
