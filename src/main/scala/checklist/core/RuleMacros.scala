package checklist.core

import scala.language.experimental.macros
import scala.language.higherKinds
import scala.reflect.macros.blackbox.Context

class RuleMacros(val c: Context) {
  import c.universe._

  def field[A: c.WeakTypeTag, B: c.WeakTypeTag](accessor: c.Tree)(rule: c.Tree): c.Tree = {
    val a = weakTypeOf[A]
    val b = weakTypeOf[B]
    val name = accessorName(accessor)
    val path = q"""${name.toString} :: _root_.checklist.core.PNil"""
    val lens = q"""monocle.macros.GenLens[$a].apply[$b](_.$name)"""
    q"${c.prefix}.field($path, $lens)($rule)"
  }

  def fieldWith[A: c.WeakTypeTag, B: c.WeakTypeTag](accessor: c.Tree)(builder: c.Tree): c.Tree = {
    val a = weakTypeOf[A]
    val b = weakTypeOf[B]
    val name = accessorName(accessor)
    val path = q"""${name.toString} :: _root_.checklist.core.PNil"""
    val lens = q"""monocle.macros.GenLens[$a].apply[$b](_.$name)"""
    q"${c.prefix}.fieldWith($path, $lens)($builder)"
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
