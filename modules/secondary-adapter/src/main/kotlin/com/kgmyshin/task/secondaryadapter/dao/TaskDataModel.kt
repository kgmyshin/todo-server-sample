package com.kgmyshin.task.secondaryadapter.dao

import java.util.*

internal data class TaskDataModel(
  var id: String? = null,
  var userId: String? = null,
  var title: String? = null,
  var description: String? = null,
  var completed: Boolean? = null,
  var createdAt: Date? = null,
  var updatedAt: Date? = null
)
