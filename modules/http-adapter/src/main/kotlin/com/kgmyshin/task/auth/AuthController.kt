package com.kgmyshin.task.auth

import com.kgmyshin.task.auth.request.SignUpRequest
import com.kgmyshin.task.auth.response.RefreshResponse
import com.kgmyshin.task.auth.response.SignInResponse
import com.kgmyshin.task.auth.response.SignUpResponse
import com.kgmyshin.task.domain.auth.InvalidateRefresthTokenError
import com.kgmyshin.task.domain.auth.PasswordFactory
import com.kgmyshin.task.domain.user.UserId
import com.kgmyshin.task.usecase.auth.RefreshTokenUseCase
import com.kgmyshin.task.usecase.auth.SignInUseCase
import com.kgmyshin.task.usecase.auth.SignUpUseCase
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@RestController
@RequestMapping("auth")
class AuthController {

  @Autowired
  private lateinit var passwordFactory: PasswordFactory
  @Autowired
  private lateinit var signUpUseCase: SignUpUseCase
  @Autowired
  private lateinit var signInUseCase: SignInUseCase
  @Autowired
  private lateinit var refreshTokenUseCase: RefreshTokenUseCase

  @RequestMapping(value = ["/signup"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
  @ResponseBody
  fun signup(@RequestBody request: SignUpRequest): Mono<SignUpResponse> = mono {
    val result = signUpUseCase.execute(
      userId = UserId(request.userId),
      password = passwordFactory.create(request.password)
    )
    SignUpResponse(
      userId = result.userId.value,
      token = result.token.value,
      refreshToken = result.refreshToken.value
    )
  }

  @RequestMapping(value = ["/signin"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
  @ResponseBody
  fun signin(@RequestBody request: SignUpRequest): Mono<SignInResponse> = mono {
    val result = signInUseCase.execute(
      userId = UserId(request.userId),
      password = passwordFactory.create(request.password)
    )

    SignInResponse(
      userId = result.userId.value,
      token = result.token.value,
      refreshToken = result.refreshToken.value
    )
  }

  @RequestMapping(value = ["/refresh"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
  @ResponseBody
  fun refresh(
    @RequestHeader("Authorization") header: String
  ) = mono {
    val parts = header.split("Bearer")
    if (parts.size != 2) {
      throw ResponseStatusException(
        HttpStatus.UNAUTHORIZED,
        "invalid refresh token"
      )
    }
    val token = parts[1].trim()
    val result = try {
      refreshTokenUseCase.execute(token)
    } catch (e: InvalidateRefresthTokenError) {
      throw ResponseStatusException(
        HttpStatus.UNAUTHORIZED, "invalid refresh token"
      )
    }

    RefreshResponse(
      userId = result.userId.value,
      token = result.token.value,
      refreshToken = result.refreshToken.value
    )
  }
}
