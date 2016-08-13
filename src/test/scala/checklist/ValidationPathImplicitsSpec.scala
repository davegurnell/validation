// package checklist

// import org.specs2.mutable._

// class ValidationPathImplicitsSpec extends Specification {
//   "instances of ValidationPathPrefix" should {
//     "allow prefixing with a string" in {
//       ("foo" :: PNil) mustEqual PField("foo", PNil)
//     }

//     "allow prefixing with an index" in {
//       (123 :: PNil) mustEqual PIndex(123, PNil)
//     }

//     "allow prefixing with a sequence of strings" in {
//       (Seq("a", "b") :: (1 :: 2 :: PNil)) mustEqual ("a" :: "b" :: 1 :: 2 :: PNil)
//     }

//     "allow prefixing with another path" in {
//       ((1 :: 2 :: PNil) :: ("a" :: "b" :: PNil)) mustEqual (1 :: 2 :: "a" :: "b" :: PNil)
//     }
//   }
// }