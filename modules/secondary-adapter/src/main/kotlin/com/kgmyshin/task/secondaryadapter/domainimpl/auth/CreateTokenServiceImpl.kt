package com.kgmyshin.task.secondaryadapter.domainimpl.auth

import com.auth0.jwt.JWT
import com.kgmyshin.task.domain.auth.CreateTokenService
import com.kgmyshin.task.domain.auth.RefreshToken
import com.kgmyshin.task.domain.auth.Token
import com.kgmyshin.task.domain.user.UserId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateTokenServiceImpl: CreateTokenService {
  override suspend fun create(userId: UserId): Pair<Token, RefreshToken> = withContext(Dispatchers.IO) {
    val token = TokenImpl(
      JWT.create()
        .withExpiresAt(
          Date().apply {
            time += 60 * 60 * 24 * 100L
          }
        )
        .withClaim("user_id", userId.value)
        .sign(TokenConfig.algorithm)
    )
    val refreshToken = RefreshTokenImpl(
      JWT.create()
        .withExpiresAt(
          Date().apply {
            time += 60 * 60 * 24 * 2 * 100L
          }
        )
        .withClaim("user_id", userId.value)
        .sign(TokenConfig.algorithm)
    )
    token to refreshToken
  }
}
