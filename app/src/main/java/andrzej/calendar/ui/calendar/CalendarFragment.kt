package andrzej.calendar.ui.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import andrzej.calendar.R
import andrzej.calendar.databinding.FragmentCalendarBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class CalendarFragment: Fragment() {


    private lateinit var selectedDate: LocalDate
    private lateinit var binding: FragmentCalendarBinding
    private lateinit var calendarAdapter: CalendarAdapter

    private val viewModel: CalendarViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMonthView(viewModel.daysInMonthArray())
        setListeners()


    }

    private fun setMonthView(daysInMonth: List<String>) {
        selectedDate = viewModel.getDate()
        binding.monthTextView.text = monthYearFromDate(selectedDate)
        calendarAdapter = CalendarAdapter(daysInMonth)
        val layoutManager = GridLayoutManager(context, 7)
        binding.calendarRecycleView.layoutManager = layoutManager
        binding.calendarRecycleView.adapter = calendarAdapter
    }


    private fun monthYearFromDate(date: LocalDate): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }


    private fun setListeners(){
        binding.nextMonthButton.setOnClickListener{
            nextMonthAction()
        }
        binding.previousMonthButton.setOnClickListener {
            previousMonthAction()
        }
    }

    private fun previousMonthAction(){
        viewModel.previousMonth()
        setMonthView(viewModel.daysInMonthArray())
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in)
        binding.calendarRecycleView.startAnimation(animation)
        binding.monthTextView.startAnimation(animation)


    }

    private fun nextMonthAction(){
        viewModel.nextMonth()
        setMonthView(viewModel.daysInMonthArray())
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_out)
        binding.calendarRecycleView.startAnimation(animation)
        binding.monthTextView.startAnimation(animation)
    }


}