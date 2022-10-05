package andrzej.calendar.di

import android.app.Application
import andrzej.calendar.room.period_days.PeriodDataBase
import andrzej.calendar.room.period_days.PeriodDaysDao
import andrzej.calendar.room.user.UserDao
import andrzej.calendar.room.user.UserDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {


    @Singleton
    @Provides
    fun provideUserDb(context: Application): UserDataBase {
        return UserDataBase.getUserDB(context)
    }

    @Singleton
    @Provides
    fun provideUserDao(userDataBase: UserDataBase): UserDao {
        return userDataBase.userDao()
    }

    @Singleton
    @Provides
    fun provideCalendarDB(context: Application): PeriodDataBase {
        return PeriodDataBase.getPeriodDB(context)
    }

    @Singleton
    @Provides
    fun provideCalendarDao(db: PeriodDataBase): PeriodDaysDao{
        return db.calendarDao()
    }

}