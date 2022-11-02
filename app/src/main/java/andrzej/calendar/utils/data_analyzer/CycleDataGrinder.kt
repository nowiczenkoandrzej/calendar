package andrzej.calendar.utils.data_analyzer

import andrzej.calendar.repository.PeriodDaysRepository
import andrzej.calendar.room.PeriodDay
import andrzej.calendar.utils.LocalDateMapper
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import javax.inject.Inject

class CycleDataGrinder
@Inject constructor(
    private val periodDaysRepository: PeriodDaysRepository,
    private val mapper: LocalDateMapper
) {
    private var days: List<PeriodDay>
    private val periodLength = ArrayList<Int>()
    private val firstDaysOfCycle = ArrayList<LocalDate>()
    private val resultList = ArrayList<CycleData>()


    init {
        runBlocking {
            days = periodDaysRepository.getAllDays()
        }
    }

    fun getData(): List<CycleData>{
        lookForIntervals()
        return resultList
    }

    private fun lookForIntervals(){
        val list = sortAndMap()
        var counter = 1

        for (i in 1 until list.size){
            if (list[i-1].plusDays(1).equals(list[i])){
                if(counter==1)
                    firstDaysOfCycle.add(list[i-1])
                counter++
            } else {
                if(counter != 1)
                    periodLength.add(counter)
                counter = 1
            }
        }

        if(counter > 1)
            periodLength.add(counter)

        combineLists()
    }



    private fun sortAndMap(): List<LocalDate>{
        val tmp = this.days.sortedWith(
            compareBy<PeriodDay> { it.year }
                .thenBy { it.month }
                .thenBy { it.day })

        return mapper.toLocalDateList(tmp)
    }

    private fun combineLists(){
        if(periodLength.isEmpty() || firstDaysOfCycle.isEmpty()) return

        for (i in periodLength.indices){
            resultList.add(
                CycleData(
                    periodLength[i],
                    firstDaysOfCycle[i]
                )
            )
        }

    }

}