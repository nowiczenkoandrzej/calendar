package andrzej.calendar.utils

import andrzej.calendar.room.PeriodDay
import java.time.LocalDate
import javax.inject.Inject

class LocalDateMapper
@Inject constructor() {

    fun toLocalDateList(days: List<PeriodDay>): List<LocalDate>{
        val result = ArrayList<LocalDate>()
        for(day in days){
            result.add(
                LocalDate.of(
                    day.year,
                    day.month,
                    day.day
                )
            )
        }

        return result
    }

    fun fromLocalDateList(days: List<LocalDate>): List<PeriodDay>{
        val result = ArrayList<PeriodDay>()
        for(day in days) {
            result.add(
                PeriodDay(
                day = day.dayOfMonth,
                month = day.monthValue,
                year = day.year
            )
            )
        }

        return result
    }

    fun fromLocalDate(day: LocalDate): PeriodDay {
        return PeriodDay(
            day = day.dayOfMonth,
            month = day.monthValue,
            year = day.year
        )
    }


}