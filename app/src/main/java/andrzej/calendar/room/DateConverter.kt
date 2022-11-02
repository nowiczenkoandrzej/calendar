package andrzej.calendar.room

import androidx.room.TypeConverter

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