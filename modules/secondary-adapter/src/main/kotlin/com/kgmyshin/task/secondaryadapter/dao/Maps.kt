package com.kgmyshin.task.secondaryadapter.dao

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource

fun mapSqlParameterSourceOf(vararg pairs: Pair<String, Any>): MapSqlParameterSource {
  val map = MapSqlParameterSource()
  pairs.forEach { pair ->
    map.addValue(
      pair.first,
      pair.second
    )
  }
  return map
}
