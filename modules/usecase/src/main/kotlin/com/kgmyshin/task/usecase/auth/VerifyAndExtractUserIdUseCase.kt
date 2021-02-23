package com.kgmyshin.task.usecase.auth

import com.kgmyshin.task.domain.auth.TokenFactory
import com.kgmyshin.task.domain.user.UserId
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class VerifyAndExtractUserIdUseCase(
  private val tokenFactory: TokenFactory
) {
  suspend fun execute(token: String): UserId = withContext(IO) {
    val domainToken = tokenFactory.convertToToken(token)
    if (!domainToken.verify()) {
      throw IllegalArgumentException("invalid token")
    }
    return@withContext domainToken.extractUserId()
  }
}
