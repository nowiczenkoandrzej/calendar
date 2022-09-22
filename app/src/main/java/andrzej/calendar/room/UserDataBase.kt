package andrzej.calendar.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 2)
abstract class UserDataBase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{

        private var dbInstance: UserDataBase? = null

        fun getUserDB(context: Context): UserDataBase{
            if (dbInstance == null){
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDataBase::class.java,
                    "user_database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return dbInstance!!
        }

    }

}