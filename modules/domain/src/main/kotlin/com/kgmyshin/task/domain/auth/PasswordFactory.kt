package com.kgmyshin.task.domain.auth

import com.kgmyshin.task.domain.user.Password

interface PasswordFactory {
  fun create(value: String): Password
}
