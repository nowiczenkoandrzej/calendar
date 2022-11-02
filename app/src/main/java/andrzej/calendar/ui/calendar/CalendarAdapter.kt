package andrzej.calendar.ui.calendar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import andrzej.calendar.R
import andrzej.calendar.room.PeriodDay
import java.time.LocalDate

class CalendarAdapter (
    private val daysOfMonth: List<String>,
    private val periodDays: List<PeriodDay>,
    private val predictedPeriodDays: List<PeriodDay>,
    private val date: LocalDate,
    private val context: Context
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.caledar_cell, parent, false)
        val layoutParams: ViewGroup.LayoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.16666).toInt()
        return CalendarViewHolder(view, date, context)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.text = daysOfMonth[position]

        for(day in periodDays){
            if(holder.dayOfMonth.text.toString() == day.day.toString())
                holder.backgroundPeriodDay.visibility = View.VISIBLE
        }

        for (day in predictedPeriodDays){
            if(holder.dayOfMonth.text.toString()=="")
                continue

            if (isPredictedPeriod(day, holder.dayOfMonth.text.toString().toInt())) {
                holder.backgroundPredictedPeriodDay.visibility = View.VISIBLE
            } else {
                if(isWeekend(position))
                    holder.dayOfMonth.setTextColor(Color.GRAY)
                else
                    holder.dayOfMonth.setTextColor(Color.DKGRAY)
            }
        }

    }


    override fun getItemCount() = daysOfMonth.size

    private fun isWeekend(position: Int): Boolean{
        val weekendDaysPositions = listOf(5, 6, 12, 13, 19, 20, 26, 27, 33, 34)

        for (day in weekendDaysPositions){
            if(position / day == 1 && position % day == 0)
                return true
        }
        return false
    }

    private fun isPredictedPeriod(day: PeriodDay, dayOfMonth: Int ): Boolean{
        return day.month == date.monthValue && day.day == dayOfMonth

    }


    class CalendarViewHolder(
        itemView: View,
        date: LocalDate,
        context: Context
    ) : RecyclerView.ViewHolder(itemView) {

        var dayOfMonth: TextView
        var backgroundPeriodDay: View
        var backgroundPredictedPeriodDay: View
        var isMarked = false

        init {
            dayOfMonth = itemView.findViewById(R.id.cell_day_text)
            backgroundPeriodDay = itemView.findViewById(R.id.cell_day_background)
            backgroundPredictedPeriodDay = itemView.findViewById(R.id.cell_day_background_period)

            itemView.setOnLongClickListener {
                if (dayOfMonth.text.toString().isEmpty()) return@setOnLongClickListener false

                setDayBackground()
                sendIntent(date, context)
                true
            }
        }

        private fun setDayBackground(){
            if(backgroundPeriodDay.visibility == View.VISIBLE){
                backgroundPeriodDay.visibility = View.GONE
                isMarked = true
            } else {
                backgroundPeriodDay.visibility = View.VISIBLE
                isMarked = false
            }
        }

        private fun sendIntent(date: LocalDate, context: Context){
            val day = dayOfMonth.text.toString().toInt()
            val month = date.month.value
            val year = date.year

            val intent = Intent("period_day").apply {
                putExtra("day", day)
                putExtra("month", month)
                putExtra("year", year)
                putExtra("is_marked", isMarked)
            }

            LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
        }

        private fun isWeekend(position: Int): Boolean{
            val weekendDaysPositions = listOf(5, 6, 12, 13, 19, 20, 26, 27, 33, 34)

            for (day in weekendDaysPositions){
                if(position / day == 1 && position % day == 0)
                    return true
            }
            return false
        }
    }

}