package com.kgmyshin.task.domain.auth

import com.kgmyshin.task.domain.user.UserId

abstract class RefreshToken(
  val value: String
) {
  abstract fun verify(): Boolean

  abstract fun extractUserId(): UserId

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as RefreshToken

    if (value != other.value) return false

    return true
  }

  override fun hashCode(): Int {
    return value.hashCode()
  }
}
