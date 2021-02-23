package com.kgmyshin.task.usecase.task

import com.kgmyshin.task.domain.error.NotFoundError
import com.kgmyshin.task.domain.task.TaskId
import com.kgmyshin.task.domain.task.TaskRepository
import com.kgmyshin.task.domain.user.UserId
import org.springframework.stereotype.Service

@Service
class DeleteTaskUseCase(
  private val taskRepository: TaskRepository
) {
  suspend fun execute(
    userId: UserId,
    taskId: TaskId
  ) {
    if (taskRepository.findByTaskId(taskId)?.userId != userId) {
      throw NotFoundError("$taskId is not found")
    }

    taskRepository.delete(taskId)
  }
}
