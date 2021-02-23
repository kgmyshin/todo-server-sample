package com.kgmyshin.task.domain.auth

interface TokenFactory {
  fun convertToToken(value: String): Token
}
