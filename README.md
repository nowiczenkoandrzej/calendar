# Period Tracker

The second app is Period Tracker which tells you when to expect next period.

App gathers all data you provide, analizes it and predicts your future periods' time.

&nbsp;

App was built with libraries such as: 
* **Room**
* **Hilt** 

with MVVM architecture.


&nbsp;

## Room

App contains one **Dao** interface for all **SQLite** queries.
```kotlin
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
```
[Source code](https://github.com/nowiczenkoandrzej/calendar/blob/master/app/src/main/java/andrzej/calendar/room/CaledarDao.kt)
&nbsp;

There are also two tables (User and PeriodDay) for storing data provided by user.
```kotlin
@Entity(primaryKeys = ["day", "month", "year"])
data class PeriodDay(

    val day: Int,
    var month: Int,
    var year: Int,
    
)
```
[Source code](https://github.com/nowiczenkoandrzej/calendar/blob/master/app/src/main/java/andrzej/calendar/room/PeriodDay.kt)
&nbsp;

There is also **DateConverter** class made for storing **PeriodDays** entities as a **String** in table.
```kotlin
class DateConverter {

    @TypeConverter
    fun fromPeriodDayToString(periodDay: PeriodDay): String {
        return periodDay.toString()
    }

    @TypeConverter
    fun fromStringToPeriodDay(day: String): PeriodDay {

        val firstComa = day.indexOf("-")
        val lastComa = day.lastIndexOf("-")

        return PeriodDay(
            day.subSequence(0, firstComa).toString().toInt(),
            day.subSequence(firstComa + 1, lastComa).toString().toInt(),
            day.subSequence(lastComa + 1, day.length).toString().toInt()
        )
    }
}
```
[Source code](https://github.com/nowiczenkoandrzej/calendar/blob/master/app/src/main/java/andrzej/calendar/room/DateConverter.kt)
&nbsp;

All classes and interface presented above was needed for creating an actual **DataBase**
```kotlin
@Database(entities = [User::class, PeriodDay::class], version = 6)
@TypeConverters(DateConverter::class)
abstract class DataBase: RoomDatabase() {

    abstract fun userDao(): CalendarDao
    
    ...
    
}
```
[Source code](https://github.com/nowiczenkoandrzej/calendar/blob/master/app/src/main/java/andrzej/calendar/room/DataBase.kt)
&nbsp;

## Hilt

App is built using **dependency injection** pattern via **Hilt** library.
App has **RoomModule** object for creating _calendarDao_ and _Database_ objects.
Both dependencies are scoped to ViewModel lifecycles.
```kotlin
@Module
@InstallIn(ViewModelComponent::class)
object RoomModule {

    @ViewModelScoped
    @Provides
    fun provideUserDb(context: Application): DataBase {
        return DataBase.getUserDB(context)
    }

    @ViewModelScoped
    @Provides
    fun provideUserDao(dataBase: DataBase): CalendarDao {
        return dataBase.userDao()
    }
}
```
[Source code](https://github.com/nowiczenkoandrzej/calendar/blob/master/app/src/main/java/andrzej/calendar/di/RoomModule.kt)
&nbsp;


## Design patterns

### Singleton
Beside **dependency injection** there is also  one **singleton** used in app. It is single **RoomDataBase** instance.
```kotlin
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
```
[Source code](https://github.com/nowiczenkoandrzej/calendar/blob/master/app/src/main/java/andrzej/calendar/room/DataBase.kt)
&nbsp;




