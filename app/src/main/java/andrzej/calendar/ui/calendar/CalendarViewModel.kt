package andrzej.calendar.ui.calendar

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel
@Inject constructor(
        private var selectedDate: LocalDate
    ): ViewModel() {


    fun getDate(): LocalDate{
        return selectedDate
    }

    fun previousMonth(){
        selectedDate = selectedDate.minusMonths(1)
    }

    fun nextMonth(){
        selectedDate = selectedDate.plusMonths(1)
    }

    fun daysInMonthArray(): List<String> {
        val daysInMonthArray = ArrayList<String>()
        val yearMonth = YearMonth.from(selectedDate)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        for (i in 1..42){
            if (i < dayOfWeek || i > daysInMonth + dayOfWeek - 1){
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add("${i - dayOfWeek + 1}")
            }
        }

        return daysInMonthArray
    }

}