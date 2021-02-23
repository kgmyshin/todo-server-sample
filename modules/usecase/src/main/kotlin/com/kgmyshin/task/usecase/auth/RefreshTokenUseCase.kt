package com.kgmyshin.task.usecase.auth

import com.kgmyshin.task.domain.auth.RefreshToken
import com.kgmyshin.task.domain.auth.RefreshTokenService
import com.kgmyshin.task.domain.auth.Token
import com.kgmyshin.task.domain.user.UserId
import org.springframework.stereotype.Service

@Service
class RefreshTokenUseCase(
  private val refreshTokenService: RefreshTokenService
) {
  suspend fun execute(token: String): RefreshTokenUseCaseResult {
    val (token, refreshToken) = refreshTokenService.refresh(token)
    return RefreshTokenUseCaseResult(
      userId = token.extractUserId(),
      token = token,
      refreshToken = refreshToken
    )
  }
}

data class RefreshTokenUseCaseResult(
  val userId: UserId,
  val token: Token,
  val refreshToken: RefreshToken
)
