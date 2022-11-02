package andrzej.calendar.room

import androidx.room.Entity
import java.time.LocalDate


@Entity(primaryKeys = ["day", "month", "year"])
data class PeriodDay(

    val day: Int,
    var month: Int,
    var year: Int,

    ) {
    override fun toString(): String {
        return "$day-$month-$year"
    }

    companion object {
        fun today(): PeriodDay {
            val today = LocalDate.now()
            return PeriodDay(
                day = today.dayOfMonth,
                month = today.monthValue,
                year = today.year
            )
        }
    }
}