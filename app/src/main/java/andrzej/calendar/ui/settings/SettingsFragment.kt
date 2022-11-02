package andrzej.calendar.ui.settings

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import andrzej.calendar.databinding.FragmentSettingsBinding
import andrzej.calendar.room.PeriodDay
import andrzej.calendar.room.User
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)
    private var initialDate = PeriodDay.today()

    private val viewModel: SettingsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
        setListener()
        viewModel.getUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user == null)
                displayMessage()
            else
                displayUserData(user)
        }

        viewModel.averageData.observe(viewLifecycleOwner) { data ->
            binding.textInputAveragePeriodLength.setText(String.format("%.2f", data.periodLength))
            binding.textInputAverageCycleLength.setText(String.format("%.2f", data.cycleLength))
            viewModel.updateInitialDate(data.lastPeriod)
        }
    }

    private fun displayUserData(user: User){
        binding.textInputPeriodLength.setText(user.periodLength)
        binding.textInputCycleLength.setText(user.cycleLength)
        binding.initialDate.text = user.initialDay.toString()
    }

    private fun displayMessage(){
        Toast.makeText(context,
            "Wprowadź i zapisz dane",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setListener(){
        binding.buttonSave.setOnClickListener {
            saveData()
        }

        binding.pickInitialDate.setOnClickListener {
            pickDate()
        }

    }

    private fun saveData(){
        if(
            binding.textInputCycleLength.text.toString().isEmpty() ||
            binding.textInputPeriodLength.text.toString().isEmpty() ||
            binding.initialDate.text.toString().isEmpty()
        ){
            Toast.makeText(context,
                "Musisz wprowadzić dane",
                Toast.LENGTH_SHORT
            ).show()
            return

        } else {

            viewModel.saveUser(
                User(
                userId = 1,
                periodLength = binding.textInputPeriodLength.text.toString(),
                cycleLength = binding.textInputCycleLength.text.toString(),
                initialDay = initialDate
            )
            )

            Toast.makeText(context,
                "Zapisano dane",
                Toast.LENGTH_SHORT
            ).show()
            hideKeyboard(activity)
        }
    }

    private fun hideKeyboard(activity: FragmentActivity?){

        if(activity == null)
            return
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if(view == null)
            view = View(activity)
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }

    private fun pickDate(){
        DatePickerDialog(
            requireContext(),
            { _, mYear, mMonth, mDay ->
                binding.initialDate.text = "$mDay-${mMonth+1}-$mYear"

                val newMonth = mMonth + 1
                initialDate = PeriodDay(
                    day = mDay,
                    month = newMonth,
                    year = mYear
                )
            }, year, month,day).show()

    }



}