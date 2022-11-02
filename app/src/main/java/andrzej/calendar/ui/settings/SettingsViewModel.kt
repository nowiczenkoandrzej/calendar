package andrzej.calendar.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import andrzej.calendar.repository.UserRepository
import andrzej.calendar.room.PeriodDay
import andrzej.calendar.room.User
import andrzej.calendar.utils.data_analyzer.AverageUserData
import andrzej.calendar.utils.data_analyzer.DataAnalyzer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel
@Inject constructor(
        private val repository: UserRepository,
        private val dataAnalyzer: DataAnalyzer
    ): ViewModel() {

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> get() = _user

    private val _averageData: MutableLiveData<AverageUserData> = MutableLiveData()
    val averageData: LiveData<AverageUserData> get() = _averageData

    init {
        _averageData.value = dataAnalyzer.get()
    }
    fun getUser() {
        viewModelScope.launch {
            _user.value = repository.getUser()
        }
    }

    fun saveUser(user: User){
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun updateInitialDate(day: PeriodDay){
        viewModelScope.launch {
            repository.updateInitialDate(day)
        }
    }

}


