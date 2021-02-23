package com.kgmyshin.task.domain.user

import com.kgmyshin.supports.ddd.Entity

class User(
  id: UserId,
  val encryptedPassword: EncryptedPassword
): Entity<UserId>(id)
