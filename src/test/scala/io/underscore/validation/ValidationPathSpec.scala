package io.underscore.validation

import org.specs2.mutable._

class ValidationPathSpec extends Specification {
  "validationPath.pathString" should {
    "work for an empty path" in {
      PNil.pathString mustEqual ""
    }

    "work for a single field" in {
      ("field" :: PNil).pathString mustEqual "field"
    }

    "work for a nested fields" in {
      ("foo" :: "bar" :: PNil).pathString mustEqual "foo.bar"
    }

    "work for a single index" in {
      (123 :: PNil).pathString mustEqual "[123]"
    }

    "work for a nested indices" in {
      (123 :: 456 :: PNil).pathString mustEqual "[123][456]"
    }

    "work for interleaved fields and indices" in {
      ("a" :: "b" :: 3 :: "c" :: 4 :: 5 :: "d" :: PNil).pathString mustEqual "a.b[3].c[4][5].d"
    }
  }

  "ValidationPath.unapply" should {
    "parse empty path" in {
      ValidationPath.unapply("") must beSome(PNil)
    }

    "parse single field" in {
      ValidationPath.unapply("a") must beSome("a" :: PNil)
    }

    "parse nested fields" in {
      ValidationPath.unapply("a.b.c") must beSome("a" :: "b" :: "c" :: PNil)
    }

    "parse single index" in {
      ValidationPath.unapply("a[1]") must beSome("a" :: 1 :: PNil)
    }

    "parse nested indices" in {
      ValidationPath.unapply("a[1][23][456]") must beSome("a" :: 1 :: 23 :: 456 :: PNil)
    }
  }
}