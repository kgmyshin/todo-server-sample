package com.kgmyshin.task.usecase.auth

import com.kgmyshin.task.domain.auth.CreateTokenService
import com.kgmyshin.task.domain.auth.RefreshToken
import com.kgmyshin.task.domain.auth.Token
import com.kgmyshin.task.domain.user.Password
import com.kgmyshin.task.domain.user.UserId
import com.kgmyshin.task.domain.user.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class SignInUseCase(
  private val userRepository: UserRepository,
  private val createTokenService: CreateTokenService
) {
  suspend fun execute(
    userId: UserId,
    password: Password
  ): SignInUseCaseResult = withContext(IO) {
    val signInUser = userRepository.findByUserId(userId) ?: throw IllegalArgumentException("not found user")

    val encryptedPassword = password.encrypt()
    if (signInUser.encryptedPassword != encryptedPassword) throw IllegalArgumentException("invalid password")

    val (token, refreshToken) = createTokenService.create(signInUser.id)
    return@withContext SignInUseCaseResult(
      userId = signInUser.id,
      token = token,
      refreshToken = refreshToken
    )
  }
}

data class SignInUseCaseResult(
  val userId: UserId,
  val token: Token,
  val refreshToken: RefreshToken
)
