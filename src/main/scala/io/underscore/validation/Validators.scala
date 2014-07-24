package io.underscore.validation

import scala.util.matching.Regex
import scala.math.Ordering

object Validators extends Validators

trait Validators {
  def pass: Seq[ValidationResult] =
    Seq.empty

  def fail(msg: => String): Seq[ValidationResult] =
    Seq(ValidationError(msg))

  def warn(msg: => String): Seq[ValidationResult] =
    Seq(ValidationWarning(msg))

  def validate[A]: Validator[A] =
    Validator[A] { in => pass }

  def validate[A](msg: => String)(func: A => Boolean): Validator[A] =
    Validator[A] { in => if(func(in)) pass else fail(msg) }

  def warn[A](rule: Validator[A]) =
    Validator[A] { in => rule(in).toWarnings }

  def optional[A](rule: Validator[A]): Validator[Option[A]] =
    Validator[Option[A]] { in => in map rule getOrElse pass }

  def required[A](rule: Validator[A]): Validator[Option[A]] =
    required("Value is required", rule)

  def required[A](msg: => String, rule: Validator[A]): Validator[Option[A]] =
    Validator[Option[A]] { in => in map rule getOrElse fail(msg) }

  def eql[A](comp: A): Validator[A] =
    eql(comp, s"Must be $comp")

  def eql[A](comp: A, msg: => String): Validator[A] =
    Validator[A] { in => if(in == comp) pass else fail(msg) }

  def neq[A](comp: A): Validator[A] =
    neq(comp, s"Must not be $comp")

  def neq[A](comp: A, msg: => String): Validator[A] =
    Validator[A] { in => if(in == comp) fail(msg) else pass }

  def lt[A](comp: A)(implicit order: Ordering[A]): Validator[A] =
    lt(comp, s"Must be less than $comp")

  def lt[A](comp: A, msg: => String)(implicit order: Ordering[A]): Validator[A] =
    Validator[A] { in => if(order.lt(in, comp)) pass else fail(msg) }

  def lte[A](comp: A)(implicit order: Ordering[A]): Validator[A] =
    lte(comp, s"Must be $comp or less")

  def lte[A](comp: A, msg: => String)(implicit order: Ordering[A]): Validator[A] =
    Validator[A] { in => if(order.lteq(in, comp)) pass else fail(msg) }

  def gt[A](comp: A)(implicit order: Ordering[A]): Validator[A] =
    gt(comp, s"Must be greater than $comp")

  def gt[A](comp: A, msg: => String)(implicit order: Ordering[A]): Validator[A] =
    Validator[A] { in => if(order.gt(in, comp)) pass else fail(msg) }

  def gte[A](comp: A)(implicit order: Ordering[A]): Validator[A] =
    gte(comp, s"Must be $comp or higher")

  def gte[A](comp: A, msg: => String)(implicit order: Ordering[A]): Validator[A] =
    Validator[A] { in => if(order.gteq(in, comp)) pass else fail(msg) }

  // TODO: Update to work with arbitrary sequences
  def nonEmpty: Validator[String] =
    nonEmpty(s"Must not be empty")

  // TODO: Update to work with arbitrary sequences
  def nonEmpty(msg: => String): Validator[String] =
    Validator[String] { in => if(in.isEmpty) fail(msg) else pass }

  // TODO: Update to work with arbitrary sequences
  def lengthLt(comp: Int): Validator[String] =
    lengthLt(comp, s"Length must be less than $comp")

  // TODO: Update to work with arbitrary sequences
  def lengthLt(comp: Int, msg: => String): Validator[String] =
    Validator[String] { in => if(in.length < comp) pass else fail(msg) }

  // TODO: Update to work with arbitrary sequences
  def lengthLte(comp: Int): Validator[String] =
    lengthLte(comp, s"Length must be at most $comp")

  // TODO: Update to work with arbitrary sequences
  def lengthLte(comp: Int, msg: => String): Validator[String] =
    Validator[String] { in => if(in.length <= comp) pass else fail(msg) }

  // TODO: Update to work with arbitrary sequences
  def lengthGt(comp: Int): Validator[String] =
    lengthGt(comp, s"Length must be more than $comp")

  // TODO: Update to work with arbitrary sequences
  def lengthGt(comp: Int, msg: => String): Validator[String] =
    Validator[String] { in => if(in.length > comp) pass else fail(msg) }

  // TODO: Update to work with arbitrary sequences
  def lengthGte(comp: Int): Validator[String] =
    lengthGte(comp, s"Length must be at least $comp")

  // TODO: Update to work with arbitrary sequences
  def lengthGte(comp: Int, msg: => String): Validator[String] =
    Validator[String] { in => if(in.length >= comp) pass else fail(msg) }

  def matchesRegex(regex: Regex, msg: => String): Validator[String] =
    Validator[String] { in => if(regex.findFirstIn(in).isDefined) pass else fail(msg) }

  def notContainedIn[A](values: => Seq[A]): Validator[A] =
    notContainedIn(values, s"Must not be one of the values ${values.mkString(", ")}")

  def notContainedIn[A](values: => Seq[A], msg: => String): Validator[A] =
    Validator[A] { in => if(values contains in) fail(msg) else pass }

  def containedIn[A](values: => Seq[A]): Validator[A] =
    containedIn(values, s"Must be one of the values ${values.mkString(", ")}")

  def containedIn[A](values: => Seq[A], msg: => String): Validator[A] =
    Validator[A] { in => if(values contains in) pass else fail(msg) }
}
