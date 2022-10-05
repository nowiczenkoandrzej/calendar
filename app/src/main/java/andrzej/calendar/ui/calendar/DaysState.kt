package andrzej.calendar.ui.calendar

import andrzej.calendar.room.period_days.PeriodDay

data class DaysState(
    val list: List<PeriodDay>? = null,
    val month: Int? = null
)