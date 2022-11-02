package andrzej.calendar.utils.data_analyzer

import android.util.Log
import andrzej.calendar.room.PeriodDay
import andrzej.calendar.utils.LocalDateMapper
import java.time.Period
import javax.inject.Inject

class DataAnalyzer
@Inject constructor(
    private val dataGrinder: CycleDataGrinder,
    private val mapper: LocalDateMapper

){
    private val cycleData = dataGrinder.getData()

    fun get() = AverageUserData(
        averagePeriodLength(),
        averageCycleLength(),
        lastPeriod()
    )

    private fun averagePeriodLength(): Double{
        var counter = 0
        var sum = 0.0

        cycleData.map { it.length }
            .forEach{
                sum += it
                counter++
            }

        return sum/counter
    }

    private fun averageCycleLength(): Double {

        Log.d("asd", "averageCycleLength: $cycleData")

        var counter = 0
        var sum = 0.0
        
        val list = cycleData.map { it.firstDay }
        for(i in 0..list.size-2){
            sum += Period.between(list[i], list[i+1]).days
            counter++
        }
        
        return sum/counter
    }

    private fun lastPeriod(): PeriodDay {
        if(cycleData.isEmpty())
            return PeriodDay.today()
        return mapper.fromLocalDate(cycleData.map { it.firstDay }.last())
    }

}