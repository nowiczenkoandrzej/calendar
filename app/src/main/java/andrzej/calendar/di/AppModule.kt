package andrzej.calendar.di

import android.content.Context
import andrzej.calendar.ui.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.LocalDate
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideListOfDays(): List<String>?{
        return null
    }

    @Singleton
    @Provides
    fun provideLocalDate(): LocalDate{
        return LocalDate.now()
    }


}