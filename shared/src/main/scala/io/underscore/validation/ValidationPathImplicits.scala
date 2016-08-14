package io.underscore.validation

import scala.language.implicitConversions

trait ValidationPathPrefix[A] {
  def prefix(prefix: A, path: ValidationPath): ValidationPath
}

trait ValidationPathImplicits {
  implicit object stringPathPrefixer extends ValidationPathPrefix[String] {
    def prefix(field: String, path: ValidationPath) =
      PField(field, path)
  }

  implicit object intPathPrefixer extends ValidationPathPrefix[Int] {
    def prefix(index: Int, path: ValidationPath) =
      PIndex(index, path)
  }

  implicit object pathPathPrefixer extends ValidationPathPrefix[ValidationPath] {
    def prefix(prefix: ValidationPath, path: ValidationPath) =
      prefix ++ path
  }

  implicit object seqStringPathPrefixer extends ValidationPathPrefix[Seq[String]] {
    def prefix(fields: Seq[String], path: ValidationPath) =
      fields.foldRight(path)(PField.apply)
  }

  implicit def prefixToPath[A](prefix: A)(implicit format: ValidationPathPrefix[A]): ValidationPath =
    format.prefix(prefix, PNil)
}