package andrzej.calendar.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import andrzej.calendar.databinding.FragmentSettingsBinding
import andrzej.calendar.model.User
import andrzej.calendar.utils.UserStateEvent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var userStateEvent: UserStateEvent
    private var isUserRegistered: Boolean = false

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // registerCheck()
        setListener()

    }


    private fun registerCheck(){
        isUserRegistered =
            if(viewModel.getCycleLength() == null || viewModel.getPeriodLength() == null){
                Toast.makeText(
                    context,
                    "Musisz wprowadzić i zapisać dane",
                    Toast.LENGTH_SHORT
                ).show()
                false
            } else {
                binding.textInputPeriodLength.setText(viewModel.getPeriodLength().toString())
                binding.textInputCycleLength.setText(viewModel.getCycleLength().toString())
                true
            }

    }


    private fun setListener(){
        binding.buttonSave.setOnClickListener {
            if(binding.textInputPeriodLength.text.toString() == "" || binding.textInputCycleLength.text.toString() == ""){
                Toast.makeText(context,
                    "Musisz najpierw podać dane",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            userStateEvent = if(isUserRegistered)
                UserStateEvent.UserRegisteredEvent
            else
                UserStateEvent.UserNotRegisteredEvent

            viewModel.saveUser(userStateEvent, User(
                1,
                binding.textInputPeriodLength.text.toString(),
                binding.textInputCycleLength.text.toString()
            ))
        }
    }



}