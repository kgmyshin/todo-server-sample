package com.kgmyshin.task.auth.response

import com.fasterxml.jackson.annotation.JsonProperty

data class RefreshResponse(
  @JsonProperty("user_id") val userId: String,
  @JsonProperty("token") val token: String,
  @JsonProperty("refresh_token") val refreshToken: String
)
