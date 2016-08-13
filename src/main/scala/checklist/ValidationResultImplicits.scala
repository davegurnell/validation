// package checklist

// trait ValidationResultImplicits {
//   implicit class SeqValidationResultOps(results: Seq[ValidationResult]) {
//     def hasErrors: Boolean =
//       results exists (_.isError)

//     def hasWarnings: Boolean =
//       results exists (_.isWarning)

//     def errors: Seq[ValidationResult] =
//       results collect { case error if error.isError => error }

//     def warnings: Seq[ValidationResult] =
//       results collect { case warn if warn.isWarning => warn }

//     def toErrors: Seq[ValidationResult] =
//       results map (_.toError)

//     def toWarnings: Seq[ValidationResult] =
//       results map (_.toWarning)

//     def prefix[A: ValidationPathPrefix](prefixes: A *): Seq[ValidationResult] =
//       for {
//         result <- results
//         prefix <- prefixes
//       } yield result.prefix(prefix)

//     def withValue[A](in: A): Validated[A] =
//       Validated[A](in, results)
//   }
// }
