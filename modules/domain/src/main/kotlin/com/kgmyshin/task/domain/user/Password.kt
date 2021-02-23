package com.kgmyshin.task.domain.user

abstract class Password(
  val value: String
) {
  abstract fun encrypt(): EncryptedPassword
}
