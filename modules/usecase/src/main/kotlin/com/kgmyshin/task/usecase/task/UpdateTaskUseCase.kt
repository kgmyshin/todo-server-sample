package com.kgmyshin.task.usecase.task

import com.kgmyshin.task.domain.task.Task
import com.kgmyshin.task.domain.task.TaskId
import com.kgmyshin.task.domain.task.TaskRepository
import com.kgmyshin.task.domain.user.UserId
import org.springframework.stereotype.Service

@Service
class UpdateTaskUseCase(
  private val taskRepository: TaskRepository
) {
  suspend fun execute(
    id: TaskId,
    userId: UserId,
    title: String,
    description: String?,
    isComplete: Boolean
  ): Task? {
    val task = Task(
      id,
      userId,
      title,
      description,
      isComplete
    )
    return taskRepository.store(userId, task)
  }
}
