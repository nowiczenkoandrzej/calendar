package andrzej.calendar.ui.calendar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import andrzej.calendar.R
import andrzej.calendar.databinding.FragmentCalendarBinding
import andrzej.calendar.room.period_days.PeriodDay
import andrzej.calendar.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class CalendarFragment: Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectedDate: LocalDate
    private lateinit var calendarAdapter: CalendarAdapter
    private var periodDays: List<PeriodDay> = ArrayList()

    private val viewModel: CalendarViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setReceiver()
        subscribeCollector()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in)
        binding.calendarRecycleView.startAnimation(animation)
        binding.monthTextView.startAnimation(animation)
    }

    private fun nextMonthAction(){
        viewModel.nextMonth()
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_out)
        binding.calendarRecycleView.startAnimation(animation)
        binding.monthTextView.startAnimation(animation)
    }

    private fun setReceiver(){
        val receiver: BroadcastReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context?, intent: Intent?) {

                val day = intent?.getStringExtra("day")
                val month = intent?.getStringExtra("month")
                val year = intent?.getStringExtra("year")
                val isMarked = intent?.getBooleanExtra("is_marked", false)

                val result = PeriodDay(
                    day!!, month!!, year!!
                )

                if(isMarked == false)
                    viewModel.insertDay(result)
                else
                    viewModel.deleteDay(result)
            }
        }
        LocalBroadcastManager
            .getInstance(requireContext())
            .registerReceiver(
                receiver,
                IntentFilter("period_day")
            )
    }

    private fun subscribeCollector(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.days.collect() {
                    periodDays = it.list!!
                    setMonthView(viewModel.daysInMonthArray())
                }
            }
        }
    }

    private fun setMonthView(daysInMonth: List<String>) {
        selectedDate = viewModel.getDate()
        binding.monthTextView.text = monthYearFromDate(selectedDate)
        calendarAdapter = CalendarAdapter(daysInMonth, periodDays, selectedDate, requireContext())
        binding.calendarRecycleView.apply {
            layoutManager = GridLayoutManager(context, 7)
            adapter = calendarAdapter
        }
    }


    private fun monthYearFromDate(date: LocalDate): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }


}