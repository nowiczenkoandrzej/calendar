package andrzej.calendar.room

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE id = '1'")
    suspend fun getUser(): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Update
    suspend fun updateUser(userEntity: UserEntity)
}

