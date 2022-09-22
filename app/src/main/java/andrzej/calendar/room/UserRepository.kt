package andrzej.calendar.room

import andrzej.calendar.model.User
import andrzej.calendar.utils.DataState
import andrzej.calendar.utils.UserMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository
@Inject constructor(
    private val userDao: UserDao,
    private val userMapper: UserMapper
    ) {

    suspend fun getUser(): Flow<DataState<User>> = flow {
        emit(DataState.Loading)
        try {
            val userEntity = userDao.getUser()
            val user = userMapper.mapFromEntity(userEntity)
            emit(DataState.Success(user))
        } catch (e: Exception) {
            emit(DataState.FirstUse(e))
        }
    }

    suspend fun insertUser(user: User) {
        val result = userMapper.mapToEntity(user)
        userDao.insertUser(result)
    }


}