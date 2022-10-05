package andrzej.calendar.room.period_days

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [PeriodDay::class], version = 2)
abstract class PeriodDataBase: RoomDatabase() {

    abstract fun calendarDao(): PeriodDaysDao

    companion object{

        private var dbInstance: PeriodDataBase? = null

        fun getPeriodDB(context: Context): PeriodDataBase {
            if(dbInstance == null){
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    PeriodDataBase::class.java,
                    "period_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

            }
            return dbInstance!!
        }

    }

}