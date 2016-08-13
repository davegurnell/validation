package io.underscore.validation
package update

object UpdateValidators extends UpdateValidators

trait UpdateValidators {
  def validateUpdate[A]: UpdateValidator[A] =
    UpdateValidator[A] { (updated, original) => pass }

  def canUpdate[A]: UpdateValidator[A] =
    UpdateValidator[A] { (newValue, oldValue) => pass }

  def canChange[A](func: PartialFunction[(A, A), Boolean]): UpdateValidator[A] =
    UpdateValidator[A] { (newValue, oldValue) =>
      if((newValue == oldValue) || (func.lift apply ((newValue, oldValue)) getOrElse false)) {
        pass
      } else {
        fail("You cannot change this field to this value")
      }
    }

  def cannotUpdate[A]: UpdateValidator[A] =
    UpdateValidator[A] { (newValue, oldValue) =>
      if(newValue == oldValue) pass else fail("You cannot change this value.")
    }

  def cannotChange[A]: UpdateValidator[A] =
    cannotUpdate[A]

  def canChangeTo[A](func: PartialFunction[A, Boolean]): UpdateValidator[A] =
    UpdateValidator[A] { (newValue, oldValue) =>
      if((newValue == oldValue) || (func.lift apply newValue getOrElse false)) {
        pass
      } else {
        fail("You cannot change this field to this value")
      }
    }

  def canChangeTo[A](validator: Validator[A]): UpdateValidator[A] =
    UpdateValidator[A] { (newValue, oldValue) =>
      if((newValue == oldValue)) {
        pass
      } else {
        validator(newValue)
      }
    }
}
