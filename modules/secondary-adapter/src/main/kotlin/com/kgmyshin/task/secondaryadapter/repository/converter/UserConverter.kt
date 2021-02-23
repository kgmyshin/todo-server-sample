package com.kgmyshin.task.secondaryadapter.repository.converter

import com.kgmyshin.supports.converter.Converter
import com.kgmyshin.task.domain.user.EncryptedPassword
import com.kgmyshin.task.domain.user.User
import com.kgmyshin.task.domain.user.UserId
import com.kgmyshin.task.secondaryadapter.dao.UserDataModel

internal object UserConverter: Converter {
  fun convertToDataModel(user: User): UserDataModel = UserDataModel(
    id = user.id.value,
    encryptedPassword = user.encryptedPassword.value
  )

  fun converttaskmainModel(userDataModel: UserDataModel): User = User(
    id = UserId(require(userDataModel.id)),
    encryptedPassword = EncryptedPassword(require(userDataModel.encryptedPassword))
  )
}
