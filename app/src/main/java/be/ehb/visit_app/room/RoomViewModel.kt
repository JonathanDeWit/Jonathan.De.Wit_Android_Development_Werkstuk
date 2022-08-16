package be.ehb.visit_app.room

import androidx.lifecycle.*
import be.ehb.visit_app.Models.MonumentDetail
import be.ehb.visit_app.Models.RoomMonument
import kotlinx.coroutines.launch

class RoomViewModel(private val repository: VisitRepository): ViewModel() {
    val allWords: LiveData<List<RoomMonument>> = repository.allMonument.asLiveData()


    fun insert(word: RoomMonument) = viewModelScope.launch {
        //repository.insert(word)
    }
}

class MonumentModelFactory(private val repository: VisitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}