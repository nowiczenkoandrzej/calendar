package andrzej.calendar.room.period_days

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PeriodDaysDao {

    @Query("SELECT * FROM PeriodDay WHERE month = :month AND year = :year")
    suspend fun get(month: String, year: String): List<PeriodDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(periodDay: PeriodDay)

    @Delete
    suspend fun deleteDay(periodDay: PeriodDay)


}