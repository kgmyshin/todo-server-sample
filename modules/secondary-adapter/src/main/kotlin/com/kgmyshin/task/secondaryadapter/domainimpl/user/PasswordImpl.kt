package com.kgmyshin.task.secondaryadapter.domainimpl.user

import com.kgmyshin.task.domain.user.EncryptedPassword
import com.kgmyshin.task.domain.user.Password
import com.sun.org.apache.xml.internal.security.utils.Base64
import java.security.MessageDigest

class PasswordImpl(value: String) : Password(value) {
  override fun encrypt(): EncryptedPassword = EncryptedPassword(
    Base64.encode(MessageDigest.getInstance("SHA-256").digest(value.toByteArray())).toString()
  )
}
