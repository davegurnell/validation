Validation
==========

Work-in-progress library demonstrating a functional programming approach to data validation in Scala.

Copyright 2014-16 Dave Gurnell. Licensed [Apache 2](http://www.apache.org/licenses/LICENSE-2.0.html).

*Note: I'm working on a "sequel" to this library. Initial spikes are [here](https://github.com/davegurnell/checklist).*

Synopsis
--------

~~~ scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

import io.underscore.validation._

case class Address(house: Int, street: String)
case class Person(name: String, age: Int, address: Address)
case class Business(name: String, addresses: Seq[Address])

implicit val addressValidator: Validator[Address] =
  validate[Address].
  field(_.house)(warn(gte(1))).
  field(_.street)(warn(nonEmpty))

implicit val personValidator: Validator[Person] =
  validate[Person].
  field(_.name)(nonEmpty).
  field(_.age)(gte(1)).
  field(_.address)

implicit val businessValidator: Validator[Business] =
  validate[Business].
  field(_.name)(nonEmpty).
  seqField(_.addresses)

// Exiting paste mode, now interpreting.

import io.underscore.validation._
defined class Address
defined class Person
defined class Business
addressValidator: io.underscore.validation.Validator[Address] = <function1>
personValidator: io.underscore.validation.Validator[Person] = <function1>
businessValidator: io.underscore.validation.Validator[Business] = <function1>

scala> Person("", 0, Address(0, "")).validate.prettyPrint
res0: String =
Validated Person(,0,Address(0,)):
 - Error: name - Must not be empty
 - Error: age - Must be 1 or higher
 - Warning: address.house - Must be 1 or higher
 - Warning: address.street - Must not be empty
~~~

Design
------

Validation is performed by instances of `Validator[A]`, which is essentially a trait representing a function of signature:

    A => Seq[ValidationResult]

where a `ValidationResult` represents a validation error or warning and encapsulates a message and a Javascript-accessor-like path:

~~~ scala
scala> ValidationError("FAIL!") prefix 123 prefix "bar" prefix "foo"
res0: io.underscore.validation.ValidationError = 
  ValidationError(FAIL!,ValidationPath(foo.bar[123]))

scala> res0.message
res1: String = FAIL!

scala> res0.path.pathString
res2: String = foo.bar[123]
~~~

The library contains a DSL for constructing validators and using them to build other vaildators:

~~~ scala
scala> gte(0) and lte(3)
res0: io.underscore.validation.Validator[Int] = <function1>

scala> required(res0)
res1: io.underscore.validation.Validator[Option[Int]] = <function1>

scala> res1(None)
res2: Seq[io.underscore.validation.ValidationResult] = List(
  ValidationError(Value is required,ValidationPath()))

scala> res1(Some(-1))
res3: Seq[io.underscore.validation.ValidationResult] = List(
  ValidationError(Must be 0 or higher,ValidationPath()))
~~~

Like validation results, validators can be assocated with specific paths into the data:

~~~ scala
scala> res1 prefix "inner" prefix "outer"
res4: io.underscore.validation.Validator[Option[Int]] = <function1>

scala> res4(Some(4))
res5: Seq[io.underscore.validation.ValidationResult] = List(
  ValidationError(Must be 3 or lower,ValidationPath(outer.inner)))
~~~

The library makes use of Scala macros in certain places to automatically capture path information from the names of accessors used to drill down into data:

~~~ scala
scala> case class Address(house: Int, street: String)
defined class Address

scala> validate[Address].
     | field(_.house)(gte(1)).
     | field(_.street)(warn(nonEmpty))
res0: io.underscore.validation.Validator[Address] = <function1>

scala> res0(Address(-1, ""))
res1: Seq[io.underscore.validation.ValidationResult] = List(
  ValidationError(Must be 1 or higher,ValidationPath(house)),
  ValidationWarning(Must not be empty,ValidationPath(street)))
~~~

Contributors
------------

Many thanks to the following for their contributions:

 - [Jono Ferguson](https://github.com/jonoabroad)
 - [Richard Dallaway](https://github.com/d6y)
 - [Raúl Piaggio](https://github.com/rpiaggio)
