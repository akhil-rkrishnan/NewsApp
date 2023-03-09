package app.android.newsapp.ui.landing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.android.newsapp.MainDispatcherRule
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

    private lateinit var fakeRepository: FakeNewsRepository

    @Before
    fun setup() {
        fakeRepository = FakeNewsRepository()
        viewModel = LandingViewModel(
            newsRepository = fakeRepository,
            eventBus = EventBus()
        )
    }

    @Test
    fun `list before api call returns empty`() = runTest {
        val list = viewModel.newsState.value.articles
        assertThat(list).isEmpty()
    }

    @Test
    fun `with source techcrunch news list returns 10 items`() = runTest {
        viewModel.setSource("techcrunch")
        advanceUntilIdle()
        val list = viewModel.newsState.value.articles
        assertThat(list).isNotEmpty()
        assertThat(list.size).isEqualTo(10)
    }

    @Test
    fun `with source the-wall-street-journal news list returns 25 items`() = runTest {
        viewModel.setSource("the-wall-street-journal")
        advanceUntilIdle()
        val list = viewModel.newsState.value.articles
        assertThat(list).isNotEmpty()
        assertThat(list.size).isEqualTo(25)
    }

    @Test
    fun `with source techcrunch all item in the list has valid authors`() = runTest {
        viewModel.setSource("techcrunch")
        advanceUntilIdle()
        val newsState = viewModel.newsState.value.articles
        newsState.forEach {
            assertThat(it.author).isNotEmpty()
        }
    }

    @Test
    fun `with source the-wall-street-journal all item in the list has valid authors`() = runTest {
        viewModel.setSource("the-wall-street-journal")
        advanceUntilIdle()
        val newsState = viewModel.newsState.value.articles
        newsState.forEach {
            assertThat(it.author).isNotEmpty()
        }
    }

    @Test
    fun `empty source return empty list of articles`() = runTest {
        viewModel.setSource("")
        advanceUntilIdle()
        val newsState = viewModel.newsState.value.articles
        assertThat(newsState).isEmpty()
    }

    @Test
    fun `empty source return status failed`() = runTest {
        viewModel.setSource("")
        advanceUntilIdle()
        val status = viewModel.newsState.value.status
        assertThat(status).isNotNull()
        assertThat(status).isEqualTo("failed")
    }

    @Test
    fun `with source the-wall-street-journal total count returns 25`() = runTest {
        viewModel.setSource("the-wall-street-journal")
        advanceUntilIdle()
        val totalCount = viewModel.newsState.value.totalItems
        assertThat(totalCount).isNotNull()
        assertThat(totalCount).isEqualTo(25)
    }

    @Test
    fun `with source techcrunch total count returns 10`() = runTest {
        viewModel.setSource("techcrunch")
        advanceUntilIdle()
        val totalCount = viewModel.newsState.value.totalItems
        assertThat(totalCount).isNotNull()
        assertThat(totalCount).isEqualTo(10)
    }

    @Test
    fun `with source the-wall-street-journal status return success`() = runTest {
        viewModel.setSource("the-wall-street-journal")
        advanceUntilIdle()
        val totalCount = viewModel.newsState.value.status
        assertThat(totalCount).isNotNull()
        assertThat(totalCount).isEqualTo("success")
    }

    @Test
    fun `with source techcrunch total count status return success`() = runTest {
        viewModel.setSource("techcrunch")
        advanceUntilIdle()
        val totalCount = viewModel.newsState.value.status
        assertThat(totalCount).isNotNull()
        assertThat(totalCount).isEqualTo("success")
    }

    @Test
    fun `setting error flag to true returns valid error body`() = runTest {
        fakeRepository.setError(true)
        advanceUntilIdle()
        val errorBody = viewModel.newsState.value.apiErrorBody
        assertThat(errorBody).isNotNull()
        assertThat(errorBody!!.code).isEqualTo("invalidTestCase")
    }


}