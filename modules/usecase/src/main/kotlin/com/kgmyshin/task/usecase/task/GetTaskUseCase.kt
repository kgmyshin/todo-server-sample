package com.kgmyshin.task.usecase.task

import com.kgmyshin.task.domain.task.Task
import com.kgmyshin.task.domain.task.TaskId
import com.kgmyshin.task.domain.task.TaskRepository
import com.kgmyshin.task.domain.user.UserId
import org.springframework.stereotype.Service

@Service
class GetTaskUseCase(
  private val taskRepository: TaskRepository
) {
  suspend fun execute(
    userId: UserId,
    taskId: TaskId
  ): Task? = taskRepository.findByTaskId(taskId).let {
    if (it != null && it.userId != userId) {
      return@let null
    }
    return@let it
  }
}
