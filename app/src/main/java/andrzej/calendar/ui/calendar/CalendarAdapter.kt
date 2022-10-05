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
import andrzej.calendar.room.period_days.PeriodDay
import java.time.LocalDate

class CalendarAdapter (
    private val daysOfMonth: List<String>,
    private val periodDays: List<PeriodDay>,
    private val date: LocalDate,
    private val context: Context
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.caledar_cell, parent, false)
        val layoutParams: ViewGroup.LayoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.16666).toInt()
        Log.d("asd", "onCreateViewHolder: ${date.monthValue}")
        return CalendarViewHolder(view, date, context)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.setText(daysOfMonth.get(position))
        // changing text color on weekends
        if(isWeekend(position))
            holder.dayOfMonth.setTextColor(Color.parseColor("#ff0000"))

        for(day in periodDays){
            if(holder.dayOfMonth.text.toString() == day.day)
                holder.background.visibility = View.VISIBLE
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


    class CalendarViewHolder(
        itemView: View,
        date: LocalDate,
        context: Context
    ) : RecyclerView.ViewHolder(itemView) {

        var dayOfMonth: TextView
        var background: View


        init {
            dayOfMonth = itemView.findViewById(R.id.cell_day_text)
            background = itemView.findViewById(R.id.cell_day_background)

            itemView.setOnClickListener{

                if (dayOfMonth.text.toString().isEmpty()) return@setOnClickListener

                val day = dayOfMonth.text.toString()
                val month = date.month.value.toString()
                val year = date.year.toString()
                val isMarked: Boolean

                if(background.visibility == View.VISIBLE){
                    background.visibility = View.GONE
                    isMarked = true
                } else {
                    background.visibility = View.VISIBLE
                    isMarked = false
                }

                val intent = Intent("period_day")
                intent.putExtra("day", day)
                intent.putExtra("month", month)
                intent.putExtra("year", year)
                intent.putExtra("is_marked", isMarked)

                LocalBroadcastManager.getInstance(context).sendBroadcast(intent)

            }

        }

    }


}