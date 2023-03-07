package app.android.newsapp.ui.screens.landing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import app.android.newsapp.data.network.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val savedInstanceHandle: SavedStateHandle,
    private val newsRepository: NewsRepository
) : ViewModel() {


    fun fetchLists() {

    }

    override fun onCleared() {

    }
}