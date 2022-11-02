package andrzej.calendar.utils.data_analyzer

import andrzej.calendar.room.PeriodDay

data class AverageUserData(
    val periodLength: Double,
    val cycleLength: Double,
    val lastPeriod: PeriodDay
)