package io.underscore.validation

import scala.util.parsing.combinator._

object ValidationPathParser extends RegexParsers with JavaTokenParsers {
  override def skipWhitespace = false

  /** Entry point to the parser. */
  def apply(input: String): Option[ValidationPath] =
    parseAll(path, input).map(Some(_)).getOrElse(None)

  /** Top parser rule */
  def path: Parser[ValidationPath] =
    repsep(fieldAndIndices, ".") ^^ { _.foldRight[ValidationPath](PNil)(_ ++ _) }

  def fieldAndIndices: Parser[ValidationPath] =
    (ident ~ indices) ^^ { case field ~ indices => PField(field, indices) }

  def indices: Parser[ValidationPath] =
    rep(index) ^^ { _.foldRight[ValidationPath](PNil)(PIndex) }

  def index: Parser[Int] =
    ("[" ~> wholeNumber <~ "]") ^^ { _.toInt }
}