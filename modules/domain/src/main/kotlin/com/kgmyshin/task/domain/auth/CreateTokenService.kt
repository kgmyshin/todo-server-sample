package com.kgmyshin.task.domain.auth

import com.kgmyshin.task.domain.user.UserId

interface CreateTokenService {

  suspend fun create(userId: UserId): Pair<Token, RefreshToken>
}
