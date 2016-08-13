// package checklist

// case object PNil extends ValidationPath {
//   def pathString = ""
// }

// case class PField(field: String, rest: ValidationPath = PNil) extends ValidationPath {
//   final def pathString: String = rest match {
//     case rest: PField => s"$field.${rest.pathString}"
//     case rest         => s"$field${rest.pathString}"
//   }
// }

// case class PIndex(index: Int, rest: ValidationPath = PNil) extends ValidationPath {
//   final def pathString: String = rest match {
//     case rest: PField => s"[$index].${rest.pathString}"
//     case rest         => s"[$index]${rest.pathString}"
//   }
// }

// trait ValidationPath {
//   def pathString: String

//   def prefix[A](prefix: A)(implicit format: ValidationPathPrefix[A]) = format.prefix(prefix, this)
//   def ::[A](prefix: A)(implicit format: ValidationPathPrefix[A]) = format.prefix(prefix, this)

//   def ++(that: ValidationPath): ValidationPath = this match {
//     case PNil => that
//     case PField(field, rest) => PField(field, rest ++ that)
//     case PIndex(index, rest) => PIndex(index, rest ++ that)
//   }

//   override def toString = s"ValidationPath($pathString)"
// }

// object ValidationPath {
//   def unapply(in: String): Option[ValidationPath] = ValidationPathParser(in)
// }
