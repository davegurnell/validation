// package checklist.core

// import cats.data.Validated
// import scala.util.matching.Regex
// import scala.math.Ordering

// trait RuleSyntax {
//   self: Rules =>

//   import Message._

//   def pass[A]: Rule[A] =
//     Rule.Pass[A]()

//   def pure[A](func: A => Checked[A]): Rule[A] = Pure(func)

//   def optional[A](rule: Rule[A]): Rule[Option[A]] = Optional(rule)

//   def required[A](rule: Rule[A]): Rule[Option[A]] = required(rule, errors("Value is required"))
//   def required[A](rule: Rule[A], failure: List[Message]): Rule[Option[A]] = Required(rule, failure)

//   def eql[A](comp: A): Rule[A] = eql(comp, errors(s"Must be $comp"))
//   def eql[A](comp: A, failure: List[Message]): Rule[A] = Eql(comp, failure)

//   def neq[A](comp: A): Rule[A] = neq(comp, errors(s"Must not be $comp"))
//   def neq[A](comp: A, failure: List[Message]): Rule[A] = Neq(comp, failure)

//   def lt[A: Ordering](comp: A): Rule[A] = lt(comp, errors(s"Must be less than $comp"))
//   def lt[A: Ordering](comp: A, failure: List[Message]): Rule[A] = Lt(comp, failure)

//   def lte[A: Ordering](comp: A): Rule[A] = lte(comp, errors(s"Must be $comp or less"))
//   def lte[A: Ordering](comp: A, failure: List[Message]): Rule[A] = Lte(comp, failure)

//   def gt[A: Ordering](comp: A): Rule[A] = gt(comp, errors(s"Must be greater than $comp"))
//   def gt[A: Ordering](comp: A, failure: List[Message]): Rule[A] = Gt(comp, failure)

//   def gte[A: Ordering](comp: A): Rule[A] = gte(comp, errors(s"Must be $comp or higher"))
//   def gte[A: Ordering](comp: A, failure: List[Message]): Rule[A] = Gte(comp, failure)

//   // TODO: Update to work with arbitrary sequences
//   def nonEmpty: Rule[String] = nonEmpty(errors(s"Must not be empty"))
//   def nonEmpty(failure: List[Message]): Rule[String] = LengthGte(1, failure)

//   def lengthLt(comp: Int): Rule[String]  = lengthLt(comp, errors(s"Length must be less than $comp"))
//   def lengthLt(comp: Int, failure: List[Message]): Rule[String]  = LengthLte(comp - 1, failure)

//   def lengthLte(comp: Int): Rule[String] = lengthLte(comp, errors(s"Length must be at most $comp"))
//   def lengthLte(comp: Int, failure: List[Message]): Rule[String] = LengthLte(comp, failure)

//   def lengthGt(comp: Int): Rule[String] = lengthGt(comp, errors(s"Length must be more than $comp"))
//   def lengthGt(comp: Int, failure: List[Message]): Rule[String] = LengthGte(comp + 1, failure)

//   def lengthGte(comp: Int): Rule[String] = lengthGte(comp, errors(s"Length must be at least $comp"))
//   def lengthGte(comp: Int, failure: List[Message]): Rule[String] = LengthGte(comp, failure)

//   def matchesRegex(regex: Regex, failure: List[Message]): Rule[String] = MatchesRegex(regex, failure)

//   def containedIn[A](values: => Seq[A]): Rule[A] = containedIn(values, errors(s"Must be one of the values ${values.mkString(", ")}"))
//   def containedIn[A](values: => Seq[A], failure: List[Message]): Rule[A] = Rule.ContainedIn[A](values, failure)

//   def notContainedIn[A](values: => Seq[A]): Rule[A] = notContainedIn(values, errors(s"Must not be one of the values ${values.mkString(", ")}"))
//   def notContainedIn[A](values: => Seq[A], failure: List[Message]): Rule[A] = Rule.NotContainedIn[A](values, failure)
// }
