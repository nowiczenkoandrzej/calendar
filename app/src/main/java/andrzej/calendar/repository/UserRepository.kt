package andrzej.calendar.repository


import andrzej.calendar.room.user.User
import andrzej.calendar.room.user.UserDao
import andrzej.calendar.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository
@Inject constructor(
    private val userDao: UserDao
    ) {

    fun getUser(): Flow<DataState<User>> = flow {
        emit(DataState.Loading)
        try {
            val user = userDao.getUser()
            emit(DataState.Success(user))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }


}