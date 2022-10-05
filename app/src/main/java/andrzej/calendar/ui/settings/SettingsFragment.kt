package andrzej.calendar.ui.settings

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import andrzej.calendar.databinding.FragmentSettingsBinding
import andrzej.calendar.room.user.User
import andrzej.calendar.utils.DataState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

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
        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            when(user){
                is DataState.Success<User> -> {
                    displayProgressBar(false)
                    displayUserData(user.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayMessage()
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
                else -> {}
            }
        })
    }


    private fun displayProgressBar(isDisplayed: Boolean){
        binding.progressBar.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayUserData(user: User){
        binding.textInputPeriodLength.setText(user.periodLength)
        binding.textInputCycleLength.setText(user.cycleLength)
    }

    private fun displayMessage(){
        Toast.makeText(context,
            "Wprowadź i zapisz dane",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setListener(){
        binding.buttonSave.setOnClickListener {

            if(binding.textInputCycleLength.text.toString().isEmpty() || binding.textInputPeriodLength.text.toString().isEmpty()){

                Toast.makeText(context,
                    "Musisz wprowadzić dane",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener

            } else {

                viewModel.saveUser(User(
                    userId = 1,
                    periodLength = binding.textInputPeriodLength.text.toString(),
                    cycleLength = binding.textInputCycleLength.text.toString()
                ))

                Toast.makeText(context,
                    "Zapisano dane",
                    Toast.LENGTH_SHORT
                ).show()
                hideKeyboard(activity)
            }

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



}