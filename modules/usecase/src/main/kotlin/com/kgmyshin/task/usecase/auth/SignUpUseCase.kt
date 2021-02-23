package com.kgmyshin.task.usecase.auth

import com.kgmyshin.task.domain.auth.CreateTokenService
import com.kgmyshin.task.domain.auth.RefreshToken
import com.kgmyshin.task.domain.auth.Token
import com.kgmyshin.task.domain.user.Password
import com.kgmyshin.task.domain.user.User
import com.kgmyshin.task.domain.user.UserId
import com.kgmyshin.task.domain.user.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class SignUpUseCase(
  private val userRepository: UserRepository,
  private val createTokenService: CreateTokenService
) {
  suspend fun execute(
    userId: UserId,
    password: Password
  ) = withContext(IO) {
    if (userRepository.findByUserId(userId) != null) throw IllegalArgumentException("already exist")

    val encryptedPassword = password.encrypt()
    val user = User(
      id = userId,
      encryptedPassword = encryptedPassword
    )

    val signUpUser = userRepository.store(user)

    val (token, refreshToken) = createTokenService.create(signUpUser.id)

    return@withContext SignUpUseCaseResult(
      userId = signUpUser.id,
      token = token,
      refreshToken = refreshToken
    )
  }
}

data class SignUpUseCaseResult(
  val userId: UserId,
  val token: Token,
  val refreshToken: RefreshToken
)
