package com.kgmyshin.task.usecase.task

import com.kgmyshin.task.domain.task.Task
import com.kgmyshin.task.domain.task.TaskId
import com.kgmyshin.task.domain.task.TaskRepository
import com.kgmyshin.task.domain.user.UserId
import com.kgmyshin.task.secondaryadapter.id.IdGenerator
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class CreateTaskUseCase(
  private val taskRepository: TaskRepository,
  private val idGenerator: IdGenerator
) {
  suspend fun execute(
    userId: UserId,
    title: String,
    description: String
  ): Task = withContext(IO) {
    val task = Task(
      id = TaskId(idGenerator.generate().toString()),
      userId = userId,
      title = title,
      description = description
    )
    return@withContext taskRepository.store(
      userId,
      task
    )
  }
}
