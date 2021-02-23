package com.kgmyshin.task.usecase.task

import com.kgmyshin.task.domain.task.Task
import com.kgmyshin.task.domain.task.TaskRepository
import com.kgmyshin.task.domain.user.UserId
import org.springframework.stereotype.Service

@Service
class GetTaskListUseCase(
  private val taskRepository: TaskRepository
) {
  suspend fun execute(
    userId: UserId,
    page: Int,
    per: Int
  ): List<Task> = taskRepository.findAllByUserId(userId, page, per)
}
