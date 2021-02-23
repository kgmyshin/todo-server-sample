package com.kgmyshin.task.task.request

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateTaskRequest(
  @JsonProperty("id") val id: String,
  @JsonProperty("title") val title: String,
  @JsonProperty("description") val description: String?,
  @JsonProperty("is_completed") val isCompleted: Boolean
)
