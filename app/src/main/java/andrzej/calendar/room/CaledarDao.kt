package andrzej.calendar.room

import androidx.room.*
import andrzej.calendar.room.PeriodDay
import andrzej.calendar.room.User

@Dao
interface CalendarDao {

    @Query("SELECT * FROM user WHERE id = '1'")
    suspend fun getUser(): User

    @Query("UPDATE user SET initialDay = :day WHERE id = '1'")
    suspend fun updateInitialDate(day: PeriodDay)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM PeriodDay")
    suspend fun getAllDays(): List<PeriodDay>

    @Query("SELECT * FROM PeriodDay WHERE month = :month AND year = :year")
    suspend fun get(month: Int, year: Int): List<PeriodDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(periodDay: PeriodDay)

    @Delete
    suspend fun deleteDay(periodDay: PeriodDay)

}

