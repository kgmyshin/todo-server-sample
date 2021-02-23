package com.kgmyshin.task.secondaryadapter.repository.converter

import com.kgmyshin.supports.converter.Converter
import com.kgmyshin.task.domain.task.Task
import com.kgmyshin.task.domain.task.TaskId
import com.kgmyshin.task.domain.user.UserId
import com.kgmyshin.task.secondaryadapter.dao.TaskDataModel

internal object TaskConverter : Converter {
  fun convertToDataModel(
    userId: UserId,
    task: Task
  ): TaskDataModel = TaskDataModel(
    id = task.id.value,
    userId = userId.value,
    title = task.title,
    description = task.description,
    completed = task.isComplete
  )

  fun convertToDomainModel(taskDataModel: TaskDataModel): Task = Task(
    id = TaskId(require(taskDataModel.id)),
    userId = UserId(require(taskDataModel.userId)),
    title = require(taskDataModel.title),
    description = taskDataModel.description,
    isComplete = require(taskDataModel.completed)
  )
}
