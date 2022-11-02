package andrzej.calendar.repository

import andrzej.calendar.room.CalendarDao
import andrzej.calendar.room.PeriodDay
import javax.inject.Inject

class PeriodDaysRepository
@Inject constructor(
    private val dao: CalendarDao
){

    suspend fun getAllDays(): List<PeriodDay> {
        return dao.getAllDays()
    }

    suspend fun getDays(month: Int, year: Int): List<PeriodDay> {
        return dao.get(month, year)
    }

    suspend fun insertDay(day: PeriodDay){
        dao.insertDay(day)
    }

    suspend fun deleteDay(day: PeriodDay) {
        dao.deleteDay(day)
    }

}

