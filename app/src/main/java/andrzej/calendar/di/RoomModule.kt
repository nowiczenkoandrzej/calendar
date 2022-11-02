package andrzej.calendar.di

import android.app.Application
import andrzej.calendar.room.CalendarDao
import andrzej.calendar.room.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped



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