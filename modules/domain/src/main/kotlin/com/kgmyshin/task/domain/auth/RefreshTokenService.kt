package com.kgmyshin.task.domain.auth

interface RefreshTokenService {

  suspend fun refresh(token: String): Pair<Token, RefreshToken>
}
