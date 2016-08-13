// package checklist

// import org.specs2.mutable._

// class ValicationMacrosSpec extends Specification {
//   case class Address(house: Int, street: String)
//   case class Person(name: String, age: Int, address: Address)
//   case class Business(name: String, addresses: Seq[Address])

//   implicit val addressValidator: Validator[Address] =
//     validate[Address].
//     field(_.house)(warn(gte(1))).
//     field(_.street)(warn(nonEmpty))

//   implicit val personValidator: Validator[Person] =
//     validate[Person].
//     field(_.name)(nonEmpty).
//     field(_.age)(gte(1)).
//     field(_.address)

//   implicit val businessValidator: Validator[Business] =
//     validate[Business].
//     field(_.name)(nonEmpty).
//     seqField(_.addresses)

//   "validator.field" should {
//     "produce errors and warnings with correct paths" in {
//       val data = Person("", 0, Address(0, ""))

//       data.validate.results map (_.path) mustEqual Seq(
//         "name"    :: PNil,
//         "age"     :: PNil,
//         "address" :: "house"  :: PNil,
//         "address" :: "street" :: PNil
//       )

//       data.validate.errors map (_.path) mustEqual Seq(
//         "name" :: PNil,
//         "age"  :: PNil
//       )

//       data.validate.warnings map (_.path) mustEqual Seq(
//         "address" :: "house"  :: PNil,
//         "address" :: "street" :: PNil
//       )
//     }
//   }

//   "validator.seqField" should {
//     "produce errors and warnings with correct paths" in {
//       val data = Business("", Seq(Address(0, "Street"), Address(1, "")))

//       data.validate.results map (_.path) mustEqual Seq(
//         "name"    :: PNil,
//         "addresses" :: 0 :: "house"  :: PNil,
//         "addresses" :: 1 :: "street" :: PNil
//       )

//       data.validate.errors map (_.path) mustEqual Seq(
//         "name" :: PNil
//       )

//       data.validate.warnings map (_.path) mustEqual Seq(
//         "addresses" :: 0 :: "house"  :: PNil,
//         "addresses" :: 1 :: "street" :: PNil
//       )
//     }
//   }

// }
