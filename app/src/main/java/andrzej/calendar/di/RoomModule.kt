package andrzej.calendar.di

import android.app.Application
import andrzej.calendar.room.UserEntity
import andrzej.calendar.room.UserDao
import andrzej.calendar.room.UserDataBase
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
    fun provideUserDb(context: Application): UserDataBase{
        return UserDataBase.getUserDB(context)
    }

    @Singleton
    @Provides
    fun provideUserDao(userDataBase: UserDataBase): UserDao{
        return userDataBase.userDao()
    }

    @Singleton
    @Provides
    fun provideUser(): UserEntity{
        return UserEntity(1,  "", "")
    }

}