package andrzej.calendar.ui.settings

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import andrzej.calendar.model.User
import andrzej.calendar.room.UserRepository
import andrzej.calendar.utils.DataState
import andrzej.calendar.utils.UserStateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel
@Inject constructor(
        private val repository: UserRepository
    ): ViewModel() {


    private val _user: MutableLiveData<DataState<User>> = MutableLiveData()
    val user: LiveData<DataState<User>>
        get() = _user

    fun getUser() {
        viewModelScope.launch {
            repository.getUser().onEach { user ->
                _user.value = user
            }.launchIn(viewModelScope)
        }
    }



    fun saveUser(user: User){
        viewModelScope.launch {
            repository.insertUser(user)

        }
    }

}


