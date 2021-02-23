package com.kgmyshin.task.secondaryadapter.domainimpl.auth

import com.auth0.jwt.JWT
import com.kgmyshin.task.domain.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.util.*

@Service
class RefreshTokenServiceImpl: RefreshTokenService {
  override suspend fun refresh(token: String): Pair<Token, RefreshToken> = withContext(Dispatchers.IO) {
    val refreshToken = RefreshTokenImpl(token)
    if (!refreshToken.verify()) throw InvalidateRefresthTokenError()
    val userId = refreshToken.extractUserId()
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
    val newRefreshToken = RefreshTokenImpl(
      JWT.create()
        .withExpiresAt(
          Date().apply {
            time += 60 * 60 * 24 * 2 * 100L
          }
        )
        .withClaim("user_id", userId.value)
        .sign(TokenConfig.algorithm)
    )
    token to newRefreshToken
  }
}
