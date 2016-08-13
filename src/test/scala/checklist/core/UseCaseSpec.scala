package checklist.core

import cats.data.Ior
import cats.instances.list._
import monocle.macros.Lenses
import org.scalatest._
import scala.language.higherKinds

class UseCaseSpec extends FreeSpec with Matchers with RuleSpecHelpers {
  import Rule._

  @Lenses("_") case class Address(house: Int, street: String)
  @Lenses("_") case class Person(name: String, age: Int, address: Address)
  @Lenses("_") case class Business(name: String, addresses: List[Address])

  implicit val addressValidator: Rule1[Address] =
    Rule.pass[Address]
      .field("house", Address._house)(gte(1))
      .field("street", Address._street)(nonEmpty[String])

  val addressesValidator: Rule1[List[Address]] =
    addressValidator.seq[List]

  implicit val personValidator: Rule1[Person] =
    Rule.pass[Person]
      .field("name", Person._name)(nonEmpty[String])
      .field("age", Person._age)(gte(1))
      .field("address", Person._address)(addressValidator)

  implicit val businessValidator: Rule1[Business] =
    Rule.pass[Business]
      .field("name", Business._name)(nonEmpty[String])
      .field("addresses", Business._addresses)(addressValidator.seq[List])

  "example from the readme" - {
    "should validate a valid person" in {
      val bananaman = Person("Eric Wimp", 11, Address(29, "Acacia Road"))
      personValidator(bananaman) should be(Ior.right(bananaman))
    }

    "should validate an invalid person" in {
      val invalid = Person("", 0, Address(0, ""))
      personValidator(invalid) should be(Ior.both(
        errors(
          ("name" :: PNil)                -> "Must not be empty",
          ("age"  :: PNil)                -> "Must be greater than or equal to 1",
          ("address" :: "house"  :: PNil) -> "Must be greater than or equal to 1",
          ("address" :: "street" :: PNil) -> "Must not be empty"
        ),
        invalid
      ))
    }
  }
}
