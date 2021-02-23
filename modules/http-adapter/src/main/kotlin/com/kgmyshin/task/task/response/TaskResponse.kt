package com.kgmyshin.task.task.response

import com.fasterxml.jackson.annotation.JsonProperty

data class TaskResponse(
  @JsonProperty("id") val id: String,
  @JsonProperty("title") val title: String,
  @JsonProperty("description") val description: String?,
  @JsonProperty("is_completed") val isCompleted: Boolean
)
