package com.kgmyshin.task.task.request

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateTaskRequest(
  @JsonProperty("title") val title: String,
  @JsonProperty("description") val description: String
)
