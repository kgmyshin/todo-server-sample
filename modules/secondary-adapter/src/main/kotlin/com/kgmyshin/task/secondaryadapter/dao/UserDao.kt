package com.kgmyshin.task.secondaryadapter.dao

import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
internal class UserDao(
  private val jdbcTemplate: NamedParameterJdbcTemplate
) {
  fun exists(userId: String): Boolean =
    jdbcTemplate.query(
      "SELECT * FROM `user` WHERE `id` = '$userId'",
      BeanPropertyRowMapper(UserDataModel::class.java)
    ).firstOrNull()?.let { true } ?: false

  fun selectByUserId(userId: String): UserDataModel? =
    jdbcTemplate.query(
      "SELECT * FROM `user` WHERE `id` = '$userId'",
      BeanPropertyRowMapper(UserDataModel::class.java)
    ).firstOrNull()

  fun insert(userDataModel: UserDataModel): UserDataModel {
    val target = userDataModel.copy(
      createdAt = Date(),
      updatedAt = Date()
    )
    jdbcTemplate.update(
      "INSERT INTO `user` (`id`, `encrypted_password`, `created_at`, `updated_at` ) VALUES ( :id, :encryptedPassword, :createdAt, :updatedAt )",
      BeanPropertySqlParameterSource(target)
    )
    return target
  }
}
