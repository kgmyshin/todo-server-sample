package com.kgmyshin.task.secondaryadapter.domainimpl.auth

import com.auth0.jwt.algorithms.Algorithm

object TokenConfig {
  private const val secret = "secret"
  val algorithm = Algorithm.HMAC256(secret)!!
}
