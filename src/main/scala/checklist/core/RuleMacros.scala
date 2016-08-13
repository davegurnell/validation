package checklist.core

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

class RuleMacros(val c: Context) {
  import c.universe._

  def field[A, B](accessor: c.Tree)(rule: c.Tree): c.Tree = {
    val name = accessorName(accessor)
    q"${c.prefix}.field(${name.toString}, _.${name})($rule)"
  }

  def seqField[A, B](accessor: c.Tree)(rule: c.Tree): c.Tree = {
    val name = accessorName(accessor)
    q"${c.prefix}.seqField(${name.toString}, _.${name})($rule)"
  }

  def fieldUpdate[A, B](accessor: c.Tree)(rule: c.Tree): c.Tree = {
    val name = accessorName(accessor)
    q"${c.prefix}.field(${name.toString}, _.${name})($rule)"
  }

  def seqFieldUpdate[A, B](accessor: c.Tree)(rule: c.Tree): c.Tree = {
    val name = accessorName(accessor)
    q"${c.prefix}.seqField(${name.toString}, _.${name})($rule)"
  }

  private def accessorName(accessor: c.Tree) = accessor match {
    case q"($param) => $obj.$name" => name
    case other => c.abort(c.enclosingPosition, errorMessage(s"Argument is not an accessor function literal."))
  }

  private def errorMessage(prefix: String) =
    s"""
     |$prefix
     |
     |The argument must be a function literal of the form `fieldName => expression`,
     |where `fieldName` is the name of a field in the object being validated.
     |
     |Alternatively use the `field(fieldName, accessor)(validationFunction)` method,
     |which allows you to specify the field name manually.
     """.stripMargin
}
