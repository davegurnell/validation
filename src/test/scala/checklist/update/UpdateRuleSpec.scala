// package checklist
// package update

// import org.specs2.mutable._

// class UpdateRuleSpec extends Specification {
//   case class Data(val num: Int, val str: String)

//   implicit val rule: UpdateRule[Data] =
//     validateUpdate[Data].
//     field(_.num)(canChangeTo(gte(0))).
//     field(_.str)(cannotChange)

//   "validate" should {
//     "pass valid changes" in {
//       ( Data(-1, "a") validateUpdateOf Data(-1, "a") ).results.map(_.path)  mustEqual Seq.empty
//       ( Data(-1, "a") validateUpdateOf Data(-1, "a") ).errors.map(_.path)   mustEqual Seq.empty
//       ( Data(-1, "a") validateUpdateOf Data(-1, "a") ).warnings.map(_.path) mustEqual Seq.empty
//     }

//     "return errors for forbidden changes" in {
//       ( Data(-1, "b") validateUpdateOf Data(-1, "a") ).results.map(_.path)  mustEqual Seq("str" :: PNil)
//       ( Data(-1, "b") validateUpdateOf Data(-1, "a") ).errors.map(_.path)   mustEqual Seq("str" :: PNil)
//       ( Data(-1, "b") validateUpdateOf Data(-1, "a") ).warnings.map(_.path) mustEqual Seq.empty
//     }

//     "return errors for invalid changes" in {
//       ( Data(-2, "a") validateUpdateOf Data(-1, "a") ).results.map(_.path)  mustEqual Seq("num" :: PNil)
//       ( Data(-2, "a") validateUpdateOf Data(-1, "a") ).errors.map(_.path)   mustEqual Seq("num" :: PNil)
//       ( Data(-2, "a") validateUpdateOf Data(-1, "a") ).warnings.map(_.path) mustEqual Seq.empty
//     }
//   }
// }
