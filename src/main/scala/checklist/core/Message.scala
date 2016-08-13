package checklist
package core

import cats.data.NonEmptyList

sealed abstract class Message(val isError: Boolean, val isWarning: Boolean) {
  def text: String
  def path: Path

  def prefix[A: PathPrefix](prefix: A): Message = this match {
    case result @ ErrorMessage(text, path)   => result.copy(path = prefix :: path)
    case result @ WarningMessage(text, path) => result.copy(path = prefix :: path)
  }
}

final case class ErrorMessage(text: String, path: Path = PNil) extends Message(true, false)

final case class WarningMessage(text: String, path: Path = PNil) extends Message(false, true)

object Message {
  def errors(head: String, tail: String *): NonEmptyList[Message] =
    NonEmptyList.of(head, tail : _*).map(ErrorMessage(_))

  def warnings(head: String, tail: String *): NonEmptyList[Message] =
    NonEmptyList.of(head, tail : _*).map(WarningMessage(_))
}
