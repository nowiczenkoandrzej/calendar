package andrzej.calendar.room.period_days

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["day", "month", "year"])
data class PeriodDay(

    val day: String,
    val month: String,
    val year: String,

)