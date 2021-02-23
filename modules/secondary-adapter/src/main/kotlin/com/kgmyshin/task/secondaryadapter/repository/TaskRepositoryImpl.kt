package com.kgmyshin.task.secondaryadapter.repository

import com.kgmyshin.task.domain.task.Task
import com.kgmyshin.task.domain.task.TaskId
import com.kgmyshin.task.domain.task.TaskRepository
import com.kgmyshin.task.domain.user.UserId
import com.kgmyshin.task.secondaryadapter.dao.TaskDao
import com.kgmyshin.task.secondaryadapter.repository.converter.TaskConverter
import org.springframework.stereotype.Repository

@Repository
internal open class TaskRepositoryImpl(
  private val taskDao: TaskDao
) : TaskRepository {
  override suspend fun findByTaskId(id: TaskId): Task? =
    taskDao.selectById(id.value)?.let { TaskConverter.convertToDomainModel(it) }

  override suspend fun findAllByUserId(id: UserId, page: Int, per: Int): List<Task> =
    taskDao.selectAllByUserId(id.value, per, page * per).let { list ->
      list.map { TaskConverter.convertToDomainModel(it) }
    }

  override suspend fun store(userId: UserId, task: Task): Task =
    taskDao.upsert(TaskConverter.convertToDataModel(userId, task)).let { TaskConverter.convertToDomainModel(it) }

  override suspend fun update(userId: UserId, task: Task): Task? =
    taskDao.update(TaskConverter.convertToDataModel(userId, task))?.let { TaskConverter.convertToDomainModel(it) }

  override suspend fun delete(id: TaskId) {
    taskDao.delete(id.value)
  }
}
