package andrzej.calendar.room

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT period_length FROM user WHERE id = 1")
    suspend fun getPeriodLength(): String

    @Query("SELECT cycle_length FROM user WHERE id = 1")
    suspend fun getCycleLength(): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Update
    suspend fun updateUser(userEntity: UserEntity)
}

