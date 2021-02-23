package com.kgmyshin.task.auth.response

import com.fasterxml.jackson.annotation.JsonProperty

data class SignUpResponse(
  @JsonProperty("user_id") val userId: String? = null,
  @JsonProperty("token") val token: String? = null,
  @JsonProperty("refresh_token") val refreshToken: String? = null
)
