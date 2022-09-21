package andrzej.calendar.room

import andrzej.calendar.model.User
import andrzej.calendar.utils.UserMapper
import javax.inject.Inject

class UserRepository
@Inject constructor(
    private val userDao: UserDao,
    private val userMapper: UserMapper
    ){

    suspend fun getPeriodLength(): String {
        return userDao.getPeriodLength()
    }

    suspend fun getCycleLength(): String {
        return userDao.getCycleLength()
    }

    suspend fun insertUser(user: User) {
        val result = userMapper.mapToEntity(user)
        userDao.insertUser(result)
    }

    suspend fun updateUser(user: User) {
        val result = userMapper.mapToEntity(user)
        userDao.updateUser(result)
    }

}