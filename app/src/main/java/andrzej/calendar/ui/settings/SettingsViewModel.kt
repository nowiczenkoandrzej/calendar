package andrzej.calendar.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import andrzej.calendar.repository.UserRepository
import andrzej.calendar.room.user.User
import andrzej.calendar.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
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
            repository.getUser().onEach {
                _user.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun saveUser(user: User){
        viewModelScope.launch {
            repository.insertUser(user)

        }
    }

}


