package andrzej.calendar.room

import android.content.Context
import androidx.room.*

@Database(entities = [User::class, PeriodDay::class], version = 6)
@TypeConverters(DateConverter::class)
abstract class DataBase: RoomDatabase() {

    abstract fun userDao(): CalendarDao

    companion object{

        private var dbInstance: DataBase? = null

        fun getUserDB(context: Context): DataBase {
            if (dbInstance == null){
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
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