package andrzej.calendar.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val userId: Int,

    @ColumnInfo(name = "period_length")
    val periodLength: String,

    @ColumnInfo(name = "cycle_length")
    val cycleLength: String
)
