package com.kgmyshin.task.secondaryadapter.domainimpl.auth

import com.kgmyshin.task.domain.auth.Token
import com.kgmyshin.task.domain.auth.TokenFactory
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class TokenFactoryImpl : TokenFactory {
  override fun convertToToken(value: String): Token = TokenImpl(value)
}
