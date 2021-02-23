package com.kgmyshin.task.domain.task

import com.kgmyshin.supports.ddd.Entity
import com.kgmyshin.task.domain.user.UserId

class Task(
  id: TaskId,
  val userId: UserId,
  val title: String,
  val description: String?,
  val isComplete: Boolean = false
): Entity<TaskId>(id) {

  fun complete(): Task = Task(
    id,
    userId,
    title,
    description,
    true
  )

  fun reopen(): Task = Task(
    id,
    userId,
    title,
    description,
    false
  )
}
