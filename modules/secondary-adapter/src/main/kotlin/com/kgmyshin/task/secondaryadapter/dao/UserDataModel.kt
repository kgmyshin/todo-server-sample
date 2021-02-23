package com.kgmyshin.task.secondaryadapter.dao

import java.util.*

internal data class UserDataModel(
  var id: String? = null,
  var encryptedPassword: String? = null,
  var createdAt: Date? = null,
  var updatedAt: Date? = null
)
