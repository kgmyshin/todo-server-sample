package com.kgmyshin.task.secondaryadapter.dao

import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
internal class TaskDao(
  private val jdbcTemplate: NamedParameterJdbcTemplate
) {
  fun exists(id: String): Boolean =
    jdbcTemplate.query(
      "SELECT * FROM `task` WHERE `id` = '$id'",
      BeanPropertyRowMapper(TaskDataModel::class.java)
    ).firstOrNull()?.let { true } ?: false

  fun selectById(id: String): TaskDataModel? =
    jdbcTemplate.query(
      "SELECT * FROM `task` WHERE `id` = '$id'",
      BeanPropertyRowMapper(TaskDataModel::class.java)
    ).firstOrNull()

  fun selectAllByUserId(userId: String, limit: Int, offset: Int): List<TaskDataModel> =
    jdbcTemplate.query(
      "SELECT * FROM `task` WHERE `user_id` = '$userId' ORDER BY `created_at` DESC LIMIT '$limit' OFFSET '$offset'",
      BeanPropertyRowMapper(TaskDataModel::class.java))

  fun upsert(taskDataModel: TaskDataModel): TaskDataModel {
    val taskId = taskDataModel.id ?: throw IllegalArgumentException("not found id")
    return if (exists(taskId)) {
      val target = taskDataModel.copy(
        updatedAt = Date()
      )
      jdbcTemplate.update(
        "UPDATE `task` SET `title` = :title, `description` = :description, `completed` = :completed WHERE `id` = :id",
        BeanPropertySqlParameterSource(target)
      )
      target
    } else {
      val target = taskDataModel.copy(
        createdAt = Date(),
        updatedAt = Date()
      )
      jdbcTemplate.update(
        "INSERT INTO `task` (`id`, `user_id`, `title`, `description`, `completed`, `created_at`, `updated_at` ) VALUES ( :id, :userId, :title, :description, :completed, :createdAt, :updatedAt )",
        BeanPropertySqlParameterSource(target)
      )
      target
    }
  }

  fun update(taskDataModel: TaskDataModel): TaskDataModel? {
    val taskId = taskDataModel.id ?: throw IllegalArgumentException("not found id")
    return if (exists(taskId)) {
      val target = taskDataModel.copy(
        updatedAt = Date()
      )
      jdbcTemplate.update(
        "UPDATE `task` SET `title` = :title, `description` = :description, `completed` = :completed WHERE `id` = :id",
        BeanPropertySqlParameterSource(target)
      )
      target
    } else {
      null
    }
  }

  fun delete(id: String) {
    jdbcTemplate.update(
      "DELETE FROM `task` WHERE `id` = :id",
      MapSqlParameterSource().addValue("id", id)
    )
  }
}
