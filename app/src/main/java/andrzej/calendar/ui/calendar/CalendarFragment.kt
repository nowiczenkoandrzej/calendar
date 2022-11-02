package andrzej.calendar.ui.calendar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import andrzej.calendar.R
import andrzej.calendar.databinding.FragmentCalendarBinding
import andrzej.calendar.room.PeriodDay
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class CalendarFragment: Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!



    private lateinit var selectedDate: LocalDate
    private lateinit var calendarAdapter: CalendarAdapter
    private var periodDays: List<PeriodDay> = ArrayList()
    private var predictedPeriodDays: List<PeriodDay> = ArrayList()

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
        viewModel.getPredictedPeriodDays()
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

                val day = intent?.getIntExtra("day", 10)
                val month = intent?.getIntExtra("month", 1)
                val year = intent?.getIntExtra("year", 2000)
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
                viewModel.days.collect {
                    periodDays = it.periodDays!!
                    predictedPeriodDays = it.predictedPeriodDays!!
                    setMonthView()


                }
            }
        }
    }

    private fun setMonthView() {
        selectedDate = viewModel.getDate()
        val daysInMonth = daysInMonthArray()
        binding.monthTextView.text = monthYearFromDate(selectedDate)
        calendarAdapter = CalendarAdapter(daysInMonth, periodDays, predictedPeriodDays, selectedDate, requireContext())
        binding.calendarRecycleView.apply {
            layoutManager = GridLayoutManager(context, 7)
            adapter = calendarAdapter
        }

    }

    private fun monthYearFromDate(date: LocalDate): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    private fun daysInMonthArray(): List<String> {
        val daysInMonthArray = ArrayList<String>()
        val yearMonth = YearMonth.from(selectedDate)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        for (i in 1..42){
            if (i < dayOfWeek || i > daysInMonth + dayOfWeek - 1){
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add("${i - dayOfWeek + 1}")
            }
        }

        return daysInMonthArray
    }


}