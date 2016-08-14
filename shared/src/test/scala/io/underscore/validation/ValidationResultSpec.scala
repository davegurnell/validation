package io.underscore.validation

import org.specs2.mutable._

class ValidationResultSpec extends Specification {
  "a validation error" should {
    val error = ValidationError("message")

    "contain a message and a path" in {
      error.message mustEqual "message"
      error.path mustEqual PNil
    }

    "be an error and not a warning" in {
      error.isError   mustEqual true
      error.isWarning mustEqual false
    }

    "be prefixable" in {
      error.prefix("field") mustEqual ValidationError("message", "field" :: PNil)
      error.prefix(1234567) mustEqual ValidationError("message", 1234567 :: PNil)
    }

    "be convertable to a warning" in {
      error.toError   mustEqual error
      error.toWarning mustEqual ValidationWarning("message")
    }
  }

  "a validation warning" should {
    val warning = ValidationWarning("message")

    "contain a message and a path" in {
      warning.message mustEqual "message"
      warning.path mustEqual PNil
    }

    "be a warning and not an error" in {
      warning.isError   mustEqual false
      warning.isWarning mustEqual true
    }

    "be prefixable" in {
      warning.prefix("field") mustEqual ValidationWarning("message", "field" :: PNil)
      warning.prefix(1234567) mustEqual ValidationWarning("message", 1234567 :: PNil)
    }

    "be convertable to an error" in {
      warning.toError   mustEqual ValidationError("message")
      warning.toWarning mustEqual warning
    }
  }
}