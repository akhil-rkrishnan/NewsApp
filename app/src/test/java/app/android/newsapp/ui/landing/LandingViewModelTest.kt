package app.android.newsapp.ui.landing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.android.newsapp.MainDispatcherRule
import app.android.newsapp.core.EventBus
import app.android.newsapp.data.network.repository.FakeNewsRepository
import app.android.newsapp.data.network.repository.FakeNewsRepository.Companion.TECH_CRUNCH
import app.android.newsapp.data.network.repository.FakeNewsRepository.Companion.WALL_STREET_JOURNAL
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
        viewModel.setSource(TECH_CRUNCH)
        advanceUntilIdle()
        val list = viewModel.newsState.value.articles
        assertThat(list).isNotEmpty()
        assertThat(list.size).isEqualTo(10)
    }

    @Test
    fun `with source the-wall-street-journal news list returns 25 items`() = runTest {
        viewModel.setSource(WALL_STREET_JOURNAL)
        advanceUntilIdle()
        val list = viewModel.newsState.value.articles
        assertThat(list).isNotEmpty()
        assertThat(list.size).isEqualTo(25)
    }

    @Test
    fun `with source techcrunch all item in the list has valid authors`() = runTest {
        viewModel.setSource(TECH_CRUNCH)
        advanceUntilIdle()
        val newsState = viewModel.newsState.value.articles
        newsState.forEach {
            assertThat(it.author).isNotEmpty()
        }
    }

    @Test
    fun `with source the-wall-street-journal all item in the list has valid authors`() = runTest {
        viewModel.setSource(WALL_STREET_JOURNAL)
        advanceUntilIdle()
        val list = viewModel.newsState.value.articles
        assertThat(list).isNotEmpty()
        list.forEach {
            assertThat(it.author).isNotEmpty()
        }
    }

    @Test
    fun `empty source return empty list of articles`() = runTest {
        viewModel.setSource("")
        advanceUntilIdle()
        val list = viewModel.newsState.value.articles
        assertThat(list).isEmpty()
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
        viewModel.setSource(WALL_STREET_JOURNAL)
        advanceUntilIdle()
        val totalCount = viewModel.newsState.value.totalItems
        assertThat(totalCount).isNotNull()
        assertThat(totalCount).isEqualTo(25)
    }

    @Test
    fun `with source techcrunch total count returns 10`() = runTest {
        viewModel.setSource(TECH_CRUNCH)
        advanceUntilIdle()
        val totalCount = viewModel.newsState.value.totalItems
        assertThat(totalCount).isNotNull()
        assertThat(totalCount).isEqualTo(10)
    }

    @Test
    fun `with source the-wall-street-journal status return success`() = runTest {
        viewModel.setSource(WALL_STREET_JOURNAL)
        advanceUntilIdle()
        val status = viewModel.newsState.value.status
        assertThat(status).isNotNull()
        assertThat(status).isEqualTo("success")
    }

    @Test
    fun `with source techcrunch total count status return success`() = runTest {
        viewModel.setSource(TECH_CRUNCH)
        advanceUntilIdle()
        val status = viewModel.newsState.value.status
        assertThat(status).isNotNull()
        assertThat(status).isEqualTo("success")
    }

    @Test
    fun `with source techcrunch returns provider name TechCrunch`() = runTest {
        viewModel.setSource(TECH_CRUNCH)
        advanceUntilIdle()
        val providerName = viewModel.newsState.value.providerName
        assertThat(providerName).isEqualTo("TechCrunch")
    }

    @Test
    fun `with source the-wall-street-journal returns provider name The Wall Street Journal`() =
        runTest {
            viewModel.setSource(WALL_STREET_JOURNAL)
            advanceUntilIdle()
            val providerName = viewModel.newsState.value.providerName
            assertThat(providerName).isEqualTo("The Wall Street Journal")
        }

    @Test
    fun `valid source techcruch url returns true`() = runTest {
        viewModel.setSource(TECH_CRUNCH)
        advanceUntilIdle()
        val list = viewModel.newsState.value.articles
        list.forEach {
            assertThat(it.url.startsWith("https://techcrunch.com")).isTrue()
            assertThat(it.urlToImage.startsWith("https://techcrunch.com")).isTrue()
        }
    }

    @Test
    fun `valid source the-wall-street-journal url returns true`() = runTest {
        viewModel.setSource(WALL_STREET_JOURNAL)
        advanceUntilIdle()
        val list = viewModel.newsState.value.articles
        list.forEach {
            assertThat(it.url.startsWith("https://www.wsj.com")).isTrue()
            assertThat(it.urlToImage.startsWith("https://images.wsj.net")).isTrue()
        }
    }

    @Test
    fun `with source techcrunch selecting an item from the list set selectedArticle return true `() =
        runTest {
            viewModel.setSource(TECH_CRUNCH)
            advanceUntilIdle()
            val list = viewModel.newsState.value.articles
            val postionOfNewsItemInList = 3
            // validates the index is less than the list size
            assertThat(postionOfNewsItemInList).isLessThan(list.size)
            val itemFromList = list[postionOfNewsItemInList]
            viewModel.setSelectedArticle(itemFromList)
            val selectedArticle = viewModel.newsState.value.selectedArticle
            assertThat(selectedArticle).isNotNull()
            assertThat(itemFromList.content == selectedArticle!!.content).isTrue()
        }

    /**
     * Method returns true if the clicked item is saved to selected article
     * @see [LandingViewModel.setSelectedArticle]
     **/
    @Test
    fun `with source wall-street-journal selecting an item from the list set selectedArticle return true `() =
        runTest {
            viewModel.setSource(WALL_STREET_JOURNAL)
            advanceUntilIdle()
            val list = viewModel.newsState.value.articles
            val postionOfNewsItemInList = 3
            // validates the index is less than the list size
            assertThat(postionOfNewsItemInList).isLessThan(list.size)
            val itemFromList = list[postionOfNewsItemInList]
            viewModel.setSelectedArticle(itemFromList)
            val selectedArticle = viewModel.newsState.value.selectedArticle
            assertThat(selectedArticle).isNotNull()
            assertThat(itemFromList.content == selectedArticle!!.content).isTrue()
        }

    @Test
    fun `with sources techcrunch validate all selected items in the list`() = runTest {
        viewModel.setSource(TECH_CRUNCH)
        advanceUntilIdle()
        val list = viewModel.newsState.value.articles
        list.forEach {
            viewModel.setSelectedArticle(it)
            val selectedArticle = viewModel.newsState.value.selectedArticle
            // to ensure the content is for techcrunch
            val isTechCrunch =
                it.author.contains("TechCrunch")
                        && selectedArticle?.author?.contains("TechCrunch") == true
            assertThat(isTechCrunch).isTrue()

            // asserts true if the item in the list is same as selected article one each iteration
            assertThat(it == selectedArticle).isTrue()
        }
    }

    @Test
    fun `with sources  wall-street-journal validate all selected items in the list`() = runTest {
        viewModel.setSource(WALL_STREET_JOURNAL)
        advanceUntilIdle()
        val list = viewModel.newsState.value.articles
        list.forEach {
            viewModel.setSelectedArticle(it)
            val selectedArticle = viewModel.newsState.value.selectedArticle
            // to ensure the content is for wall street journal
            val isWallStreet =
                it.author.contains("Wall street author")
                        && selectedArticle?.author?.contains("Wall street author") == true
            assertThat(isWallStreet).isTrue()

            // asserts true if the item in the list is same as selected article one each iteration
            assertThat(it == selectedArticle).isTrue()
        }
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