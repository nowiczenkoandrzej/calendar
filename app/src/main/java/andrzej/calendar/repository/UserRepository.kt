package andrzej.calendar.repository


import andrzej.calendar.room.CalendarDao
import andrzej.calendar.room.PeriodDay
import andrzej.calendar.room.User
import javax.inject.Inject

class UserRepository
@Inject constructor(
    private val dao: CalendarDao
    ) {

    suspend fun getUser(): User {
        return dao.getUser()
    }

    suspend fun insertUser(user: User) {
        dao.insertUser(user)
    }

    suspend fun updateInitialDate(day: PeriodDay){
        dao.updateInitialDate(day)
    }

}