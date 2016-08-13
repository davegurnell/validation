// package checklist

// import org.specs2.mutable._

// class ValidatorSpec extends Specification {
//   "pass helper" should {
//     "create an empty list of results" in {
//       pass mustEqual Seq.empty
//     }
//   }

//   "fail helper" should {
//     "create a single error" in {
//       fail("message") mustEqual Seq(ValidationError("message"))
//     }
//   }

//   "warn helper" should {
//     "create a single warning" in {
//       warn("message") mustEqual Seq(ValidationWarning("message"))
//     }
//   }

//   "validate[A]" >> {
//     val validator = validate[Int]
//     validator(+1) mustEqual pass
//     validator(-1) mustEqual pass
//   }

//   val isEven = validate[Int]("Value must be even")(i => i % 2 == 0)

//   "validate(message)(test)" >> {
//     val validator = isEven
//     validator(0) mustEqual pass
//     validator(1) mustEqual fail("Value must be even")
//   }

//   "warn constructor" >> {
//     val validator = warn(isEven)
//     validator(0) mustEqual pass
//     validator(1) mustEqual warn("Value must be even")
//   }

//   "optional" >> {
//     val validator = optional(isEven)
//     validator(None)    mustEqual pass
//     validator(Some(0)) mustEqual pass
//     validator(Some(1)) mustEqual fail("Value must be even")
//   }

//   "required" >> {
//     val validator = required(isEven)
//     validator(None)    mustEqual fail("Value is required")
//     validator(Some(0)) mustEqual pass
//     validator(Some(1)) mustEqual fail("Value must be even")
//   }

//   "eql" >> {
//     val validator = eql(0, "fail")
//     validator(0)  mustEqual pass
//     validator(1)  mustEqual fail("fail")
//     validator(-1) mustEqual fail("fail")
//   }

//   "neq" >> {
//     val validator = neq(0, "fail")
//     validator(0)  mustEqual fail("fail")
//     validator(1)  mustEqual pass
//     validator(-1) mustEqual pass
//   }

//   "lt" >> {
//     val validator = lt(0, "fail")
//     validator(0)  mustEqual fail("fail")
//     validator(1)  mustEqual fail("fail")
//     validator(-1) mustEqual pass
//   }

//   "lte" >> {
//     val validator = lte(0, "fail")
//     validator(0)  mustEqual pass
//     validator(1)  mustEqual fail("fail")
//     validator(-1) mustEqual pass
//   }

//   "gt" >> {
//     val validator = gt(0, "fail")
//     validator(0)  mustEqual fail("fail")
//     validator(1)  mustEqual pass
//     validator(-1) mustEqual fail("fail")
//   }

//   "gte" >> {
//     val validator = gte(0, "fail")
//     validator(0)  mustEqual pass
//     validator(1)  mustEqual pass
//     validator(-1) mustEqual fail("fail")
//   }

//   "nonEmpty" >> {
//     val validator: Validator[String] = nonEmpty("fail")
//     validator("")  mustEqual fail("fail")
//     validator(" ") mustEqual pass
//   }

//   "lengthLt"  >> pending
//   "lengthLte" >> pending
//   "lengthGt"  >> pending
//   "lengthGte" >> pending

//   "matchesRegex" >> {
//     val validator = matchesRegex("^[^@]+@[^@]+$".r, "Must be an email")
//     validator("dave@example.com")  mustEqual pass
//     validator("dave@")             mustEqual fail("Must be an email")
//     validator("@example.com")      mustEqual fail("Must be an email")
//     validator("dave@@example.com") mustEqual fail("Must be an email")
//   }

//   "notContainedIn" >> {
//     val validator = notContainedIn(List(1, 2, 3))
//     validator(0) mustEqual pass
//     validator(1) mustEqual fail("Must not be one of the values 1, 2, 3")
//     validator(2) mustEqual fail("Must not be one of the values 1, 2, 3")
//     validator(3) mustEqual fail("Must not be one of the values 1, 2, 3")
//     validator(4) mustEqual pass
//   }

//   "containedIn" >> {
//     val validator = containedIn(List(1, 2, 3))
//     validator(0) mustEqual fail("Must be one of the values 1, 2, 3")
//     validator(1) mustEqual pass
//     validator(2) mustEqual pass
//     validator(3) mustEqual pass
//     validator(4) mustEqual fail("Must be one of the values 1, 2, 3")
//   }
// }