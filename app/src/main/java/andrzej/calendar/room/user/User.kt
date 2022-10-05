package andrzej.calendar.room.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val userId: Int,

    val periodLength: String,

    val cycleLength: String
)
