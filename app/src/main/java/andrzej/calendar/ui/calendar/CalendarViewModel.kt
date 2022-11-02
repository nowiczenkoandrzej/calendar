package andrzej.calendar.ui.calendar



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import andrzej.calendar.repository.PeriodDaysRepository
import andrzej.calendar.repository.UserRepository
import andrzej.calendar.room.PeriodDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel
@Inject constructor(
        private var selectedDate: LocalDate,
        private val periodDaysRepository: PeriodDaysRepository,
        private val userRepository: UserRepository
    ): ViewModel() {



    private val _days = MutableStateFlow<List<PeriodDay>>(emptyList())
    private val _month = MutableStateFlow(selectedDate.monthValue)
    private val _predictedPeriodDays = MutableStateFlow<List<PeriodDay>>(emptyList())

    init {
        viewModelScope.launch {
            _days.value = periodDaysRepository.getDays(_month.value, selectedDate.year)
        }
    }

    val days = combine(_days, _month, _predictedPeriodDays){ days, month, predictedDays ->
        DaysState(
            periodDaysRepository.getDays(month, selectedDate.year),
            predictedDays,
            month
        )
    }

    fun insertDay(day: PeriodDay){

        viewModelScope.launch {
            periodDaysRepository.insertDay(day)
        }
    }

    fun deleteDay(day: PeriodDay){
        viewModelScope.launch {
            periodDaysRepository.deleteDay(day)
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

    fun getPredictedPeriodDays() {
        viewModelScope.launch {
            val user = userRepository.getUser() ?: return@launch

            if(user.periodLength.isBlank() || user.cycleLength.isBlank()) return@launch

            val initialDate = user.initialDay
            val periodLength = user.periodLength.toInt()
            val cycleLength = user.cycleLength.toInt()

            val date = LocalDate.of(
                initialDate.year,
                initialDate.month,
                initialDate.day
            )

            val predictionRange = (cycleLength * 3) + periodLength
            val predictedDatesIndexes = ArrayList<Int>(periodLength * 4)
            val tmpList = ArrayList<PeriodDay>()
            val result = ArrayList<PeriodDay>()

            for (i in 0 until predictionRange){
                when (i) {
                    in 0 until periodLength -> predictedDatesIndexes.add(i)
                    in cycleLength until cycleLength + periodLength -> predictedDatesIndexes.add(i)
                    in cycleLength * 2 until (cycleLength * 2) + periodLength -> predictedDatesIndexes.add(i)
                    in cycleLength * 3 until (cycleLength * 3) + periodLength -> predictedDatesIndexes.add(i)
                }
            }

            generateSequence(date) { it.plusDays(1L) }
                .take(predictionRange)
                .map { PeriodDay(
                    day = it.dayOfMonth,
                    month = it.monthValue,
                    year = it.year
                ) }
                .forEach { tmpList.add(it) }

            for(index in predictedDatesIndexes)
                result.add(tmpList[index])

            _predictedPeriodDays.value = result
        }
    }
}


