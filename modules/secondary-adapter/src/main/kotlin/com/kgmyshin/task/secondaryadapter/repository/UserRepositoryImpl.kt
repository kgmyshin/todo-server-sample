package com.kgmyshin.task.secondaryadapter.repository

import com.kgmyshin.task.domain.user.User
import com.kgmyshin.task.domain.user.UserId
import com.kgmyshin.task.domain.user.UserRepository
import com.kgmyshin.task.secondaryadapter.dao.UserDao
import com.kgmyshin.task.secondaryadapter.repository.converter.UserConverter
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Repository

@Repository
internal class UserRepositoryImpl(
  private val userDao: UserDao
) : UserRepository {
  override suspend fun findByUserId(id: UserId): User? = withContext(IO) {
    userDao.selectByUserId(id.value)?.let {
      UserConverter.convertToDomainModel(it)
    }
  }

  override suspend fun store(user: User): User = withContext(IO) {
    userDao.insert(UserConverter.convertToDataModel(user)).let {
      UserConverter.convertToDomainModel(it)
    }
  }
}
