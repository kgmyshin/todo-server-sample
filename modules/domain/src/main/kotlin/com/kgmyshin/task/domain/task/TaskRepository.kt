package com.kgmyshin.task.domain.task

import com.kgmyshin.task.domain.user.UserId

interface TaskRepository {

  suspend fun findByTaskId(id: TaskId): Task?

  suspend fun findAllByUserId(id: UserId, page: Int, per: Int): List<Task>

  suspend fun store(userId: UserId, task: Task): Task

  suspend fun update(userId: UserId, task: Task): Task?

  suspend fun delete(id: TaskId)
}
