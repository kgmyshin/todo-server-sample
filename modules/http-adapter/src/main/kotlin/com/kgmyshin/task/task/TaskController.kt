package com.kgmyshin.task.task

import com.kgmyshin.task.domain.error.NotFoundError
import com.kgmyshin.task.domain.task.TaskId
import com.kgmyshin.task.domain.user.UserId
import com.kgmyshin.task.task.request.CreateTaskRequest
import com.kgmyshin.task.task.request.UpdateTaskRequest
import com.kgmyshin.task.task.response.TaskResponse
import com.kgmyshin.task.usecase.auth.VerifyAndExtractUserIdUseCase
import com.kgmyshin.task.usecase.task.*
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@RestController
@RequestMapping("tasks")
class TaskController {

  @Autowired
  private lateinit var createTaskUseCase: CreateTaskUseCase
  @Autowired
  private lateinit var getTaskListUseCase: GetTaskListUseCase
  @Autowired
  private lateinit var getTaskUseCase: GetTaskUseCase
  @Autowired
  private lateinit var updateTaskUseCase: UpdateTaskUseCase
  @Autowired
  private lateinit var deleteTaskUseCase: DeleteTaskUseCase
  @Autowired
  private lateinit var verifyAndExtractUserIdUseCase: VerifyAndExtractUserIdUseCase

  @RequestMapping(value = ["", "/"], method = [RequestMethod.GET])
  @ResponseBody
  fun get(
    @RequestHeader("Authorization") header: String,
    @RequestParam("page") page: Int,
    @RequestParam("per") per: Int
  ): Mono<List<TaskResponse>> = mono {
    val userId = verifyAndExtractUserId(header)
    val taskList = getTaskListUseCase.execute(
      userId,
      page,
      per
    )
    return@mono taskList.map {
      TaskResponse(
        id = it.id.value,
        title = it.title,
        description = it.description,
        isCompleted = it.isComplete
      )
    }
  }

  @RequestMapping(value = ["", "/"], method = [RequestMethod.POST])
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  fun create(
    @RequestHeader("Authorization") header: String,
    @RequestBody request: CreateTaskRequest
  ): Mono<TaskResponse> = mono {
    val userId = verifyAndExtractUserId(header)
    val task = createTaskUseCase.execute(
      userId,
      request.title,
      request.description
    )
    return@mono TaskResponse(
      id = task.id.value,
      title = task.title,
      description = task.description,
      isCompleted = task.isComplete
    )
  }

  @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
  @ResponseBody
  fun get(
    @RequestHeader("Authorization") header: String,
    @PathVariable id: String
  ): Mono<TaskResponse> = mono {
    val userId = verifyAndExtractUserId(header)
    val task = getTaskUseCase.execute(
      userId,
      TaskId(id)
    ) ?: throw ResponseStatusException(
      HttpStatus.NOT_FOUND, "task not found"
    )
    return@mono TaskResponse(
      id = task.id.value,
      title = task.title,
      description = task.description,
      isCompleted = task.isComplete
    )
  }

  @RequestMapping(value = ["/{id}"], method = [RequestMethod.PUT])
  @ResponseBody
  fun update(
    @RequestHeader("Authorization") header: String,
    @RequestBody request: UpdateTaskRequest,
    @PathVariable id: String
  ): Mono<TaskResponse> = mono {
    val userId = verifyAndExtractUserId(header)
    val task = updateTaskUseCase.execute(
      TaskId(request.id),
      userId,
      request.title,
      request.description,
      request.isCompleted
    ) ?: throw ResponseStatusException(
      HttpStatus.NOT_FOUND, "task not found"
    )
    return@mono TaskResponse(
      id = task.id.value,
      title = task.title,
      description = task.description,
      isCompleted = task.isComplete
    )
  }

  @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ResponseBody
  fun delete(
    @RequestHeader("Authorization") header: String,
    @PathVariable id: String
  ) = mono {
    val userId = verifyAndExtractUserId(header)
    try {
      deleteTaskUseCase.execute(
        userId,
        TaskId(id)
      )
    } catch (e: NotFoundError) {
      throw ResponseStatusException(
        HttpStatus.NOT_FOUND, "task not found"
      )
    } catch (e: Exception) {
      throw ResponseStatusException(
        HttpStatus.INTERNAL_SERVER_ERROR, e.message
      )
    }
  }

  private suspend fun verifyAndExtractUserId(header: String): UserId {
    val parts = header.split("Bearer")
    if (parts.size != 2) throw IllegalArgumentException("invalid token")
    val token = parts[1].trim()
    return verifyAndExtractUserIdUseCase.execute(token)
  }
}
