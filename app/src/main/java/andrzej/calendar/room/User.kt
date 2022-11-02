package andrzej.calendar.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import andrzej.calendar.room.PeriodDay

@Entity
data class User(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val userId: Int,

    val periodLength: String,

    val cycleLength: String,

    val initialDay: PeriodDay
)
