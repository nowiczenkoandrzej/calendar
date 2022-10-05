package andrzej.calendar.repository

import android.util.Log
import andrzej.calendar.room.period_days.PeriodDay
import andrzej.calendar.room.period_days.PeriodDaysDao
import andrzej.calendar.utils.DataState
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PeriodDaysRepository
@Inject constructor(
    private val dao: PeriodDaysDao
){

    suspend fun getDays(month: String, year: String): List<PeriodDay> {
        return dao.get(month, year)
    }

    suspend fun insertDay(day: PeriodDay){
        dao.insertDay(day)
    }

    suspend fun deleteDay(day: PeriodDay) {
        dao.deleteDay(day)
    }

}

