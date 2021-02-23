package com.kgmyshin.task.secondaryadapter.domainimpl.user

import com.kgmyshin.task.domain.auth.PasswordFactory
import com.kgmyshin.task.domain.user.Password
import org.springframework.stereotype.Component

@Component
class PasswordFactoryImpl : PasswordFactory {
  override fun create(value: String): Password = PasswordImpl(value)
}
