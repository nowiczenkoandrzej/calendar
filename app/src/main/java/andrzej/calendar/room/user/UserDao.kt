package andrzej.calendar.room.user

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE id = '1'")
    suspend fun getUser(): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

}

