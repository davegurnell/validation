// package checklist

// import scala.language.higherKinds

// case class Validated[A](value: A, results: Seq[ValidationResult] = Seq.empty) {
//   def isSuccess   = results.isEmpty
//   def hasWarnings = results.hasWarnings
//   def hasErrors   = results.hasErrors
//   def warnings    = results.warnings
//   def errors      = results.errors

//   def and[B >: A](that: Validated[B]) =
//     Validated[A](this.value, this.results ++ that.results)

//   def prefix[A: ValidationPathPrefix](prefixes: A *) =
//     this.copy(results = results.prefix(prefixes : _*))

//   def map[B >: A](func: A => B): Validated[B] =
//     this.fold(
//       succ = _ => this.copy(value = func(value)),
//       fail = _ => this.copy(value = (value : B))
//     )

//   def flatMap[B >: A](func: A => Validated[B]): Validated[B] =
//     this.fold(
//       succ = { _ =>
//         val ans = func(value)
//         ans.copy(results = ans.results ++ this.results)
//       },
//       fail = { _ => this.copy(value = (value : B)) }
//     )

//   def foreach(func: A => Unit): Unit =
//     this.fold(succ = _ => func(value), fail = _ => ())

//   def fold[B](fail: Seq[ValidationResult] => B, succ: A => B): B =
//     if(this.hasErrors) fail(errors) else succ(value)

//   def prettyPrint: String =
//     "Validated " + value.toString + ":\n" + results.map { result =>
//       val label   = if(result.isError) "Error" else "Warning"
//       val path    = result.path.pathString
//       val message = result.message
//       s" - $label: $path - $message"
//     }.mkString("\n")
// }
