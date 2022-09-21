package andrzej.calendar.ui.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import andrzej.calendar.R

class CalendarAdapter (
    private val daysOfMonth: List<String>
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.caledar_cell, parent, false)
        val layoutParams: ViewGroup.LayoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.16666).toInt()
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.setText(daysOfMonth.get(position))
        // changing text color on weekends
        if(isWeekend(position))
            holder.dayOfMonth.setTextColor(Color.parseColor("#ff0000"))
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


    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var dayOfMonth: TextView

        init {
            dayOfMonth = itemView.findViewById(R.id.cell_day_text)
        }


    }


}