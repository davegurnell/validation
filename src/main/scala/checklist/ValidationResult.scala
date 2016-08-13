// package checklist

// sealed trait ValidationResult {
//   def message: String
//   def path: ValidationPath

//   def prefix[A: ValidationPathPrefix](prefix: A): ValidationResult

//   def isError: Boolean
//   def isWarning: Boolean

//   def toError: ValidationError
//   def toWarning: ValidationWarning
// }

// case class ValidationError(message: String, path: ValidationPath = PNil) extends ValidationResult {
//   def prefix[A: ValidationPathPrefix](prefix: A) =
//     this.copy(path = prefix :: path)

//   val isError   = true
//   val isWarning = false

//   def toError   = this
//   def toWarning = ValidationWarning(message, path)
// }

// case class ValidationWarning(message: String, path: ValidationPath = PNil) extends ValidationResult {
//   def prefix[A: ValidationPathPrefix](prefix: A) =
//     this.copy(path = prefix :: path)

//   val isError   = false
//   val isWarning = true

//   def toError   = ValidationError(message, path)
//   def toWarning = this
// }
