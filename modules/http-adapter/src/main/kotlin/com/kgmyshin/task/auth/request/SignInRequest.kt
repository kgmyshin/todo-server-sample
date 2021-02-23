package com.kgmyshin.task.auth.request

import com.fasterxml.jackson.annotation.JsonProperty

data class SignInRequest(
  @JsonProperty("user_id") val userId: String,
  @JsonProperty("password") val password: String
)
