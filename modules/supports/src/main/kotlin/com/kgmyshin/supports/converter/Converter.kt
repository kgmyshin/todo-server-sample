package com.kgmyshin.supports.converter

interface Converter {
  fun <T> require(value: T?): T = if (value == null) {
    throw IllegalArgumentException()
  } else {
    value
  }
}
