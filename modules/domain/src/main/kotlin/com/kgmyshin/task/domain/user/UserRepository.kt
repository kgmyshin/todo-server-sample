package com.kgmyshin.task.domain.user

interface UserRepository {
  suspend fun findByUserId(id: UserId): User?

  suspend fun store(user: User): User
}
