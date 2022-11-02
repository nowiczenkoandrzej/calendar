package andrzej.calendar.ui.calendar

import andrzej.calendar.room.PeriodDay

data class DaysState(
    val periodDays: List<PeriodDay>? = null,
    val predictedPeriodDays: List<PeriodDay>? = null,
    val month: Int? = null
)