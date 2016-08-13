package io.underscore.validation

import scala.language.implicitConversions

package object update extends UpdateValidators {
  implicit class UpdateValidatableOps[A : UpdateValidator](updated: A) {
    def validateUpdateOf(original: A) =
      implicitly[UpdateValidator[A]] apply (updated, original) withValue updated
  }

  implicit def functionToUpdateValidator[A](func: (A, A) => Seq[ValidationResult]): UpdateValidator[A] =
    UpdateValidator[A] { (a, b) => func(a, b) }
}
