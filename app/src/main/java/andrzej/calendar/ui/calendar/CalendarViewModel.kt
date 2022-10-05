package andrzej.calendar.ui.calendar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import andrzej.calendar.repository.PeriodDaysRepository
import andrzej.calendar.room.period_days.PeriodDay
import andrzej.calendar.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel
@Inject constructor(
        private var selectedDate: LocalDate,
        private val repository: PeriodDaysRepository
    ): ViewModel() {

    private val _days = MutableStateFlow<List<PeriodDay>>(emptyList())
    private val _month = MutableStateFlow(selectedDate.monthValue)

    init {
        viewModelScope.launch {
            _days.value = repository.getDays(_month.toString(), selectedDate.year.toString())
        }
    }

    val days = combine(_days, _month){ days, month ->
        DaysState(
            repository.getDays(month.toString(), selectedDate.year.toString()),
            month
        )
    }


    fun insertDay(day: PeriodDay){
        viewModelScope.launch {
            repository.insertDay(day)
        }
    }

    fun deleteDay(day: PeriodDay){
        viewModelScope.launch {
            repository.deleteDay(day)
        }
    }

    fun getDate(): LocalDate{
        return selectedDate
    }

    fun previousMonth(){
        selectedDate = selectedDate.minusMonths(1)
        _month.value--

        if(_month.value == 0)
            _month.value = 12
    }

    fun nextMonth(){
        selectedDate = selectedDate.plusMonths(1)
        _month.value++

        if(_month.value == 13)
            _month.value = 1
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

