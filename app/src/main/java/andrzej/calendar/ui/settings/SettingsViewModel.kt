package andrzej.calendar.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import andrzej.calendar.model.User
import andrzej.calendar.room.UserRepository
import andrzej.calendar.utils.UserStateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel
@Inject constructor(
        private val repository: UserRepository
    ): ViewModel() {

    private val periodLength: MutableLiveData<String> = MutableLiveData()
    private val cycleLength: MutableLiveData<String> = MutableLiveData()


    fun getPeriodLength(): String? {
        viewModelScope.launch {
            periodLength.value = repository.getPeriodLength()
        }
        return periodLength.value
    }

    fun getCycleLength(): String? {
        viewModelScope.launch {
            cycleLength.value = repository.getCycleLength()
        }
        return periodLength.value
    }

    private fun saveUserData(user: User){
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    private fun updateUserData(user: User){
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }

    fun saveUser(userStateEvent: UserStateEvent, user: User){
        viewModelScope.launch {
            when(userStateEvent){
                is UserStateEvent.UserRegisteredEvent -> {
                    updateUserData(user)
                }
                is UserStateEvent.UserNotRegisteredEvent -> {
                    saveUserData(user)
                }
            }
        }
    }
}


