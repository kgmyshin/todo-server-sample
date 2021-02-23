package com.kgmyshin.task.secondaryadapter.domainimpl.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTVerificationException
import com.kgmyshin.task.domain.auth.Token
import com.kgmyshin.task.domain.user.UserId

class TokenImpl(
  value: String
): Token(value) {
  override fun verify(): Boolean = try {
    JWT.require(TokenConfig.algorithm).build().verify(this.value)
    true
  } catch (e: JWTVerificationException) {
    false
  }

  override fun extractUserId(): UserId = UserId(JWT.decode(this.value).getClaim("user_id").asString())
}
