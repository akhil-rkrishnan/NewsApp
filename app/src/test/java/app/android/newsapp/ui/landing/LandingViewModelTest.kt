package app.android.newsapp.ui.landing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.android.currency_converter.MainDispatcherRule
import app.android.newsapp.core.EventBus
import app.android.newsapp.data.network.repository.FakeNewsRepository
import app.android.newsapp.ui.screens.landing.LandingViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LandingViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecuterRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LandingViewModel

    @Before
    fun setup() {
        viewModel = LandingViewModel(
            newsRepository = FakeNewsRepository(),
            eventBus = EventBus()
        )
    }

    @Test
    fun `list before api call returns empty`() = runTest {
        assertThat(viewModel.newsList.value).isEmpty()
    }

    @Test
    fun `list after first api call with source tech crunch returns 10 items`() = runTest {
        advanceUntilIdle()
        val list = viewModel.newsList.value
        assertThat(list).isNotEmpty()
        assertThat(list.size).isEqualTo(10)
    }
}