package app.android.newsapp.ui.landing

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.filters.MediumTest
import app.android.newsapp.core.EventBus
import app.android.newsapp.data.network.repository.FakeNewsRepository
import app.android.newsapp.data.network.repository.FakeNewsRepository.Companion.TECH_CRUNCH
import app.android.newsapp.data.network.repository.FakeNewsRepository.Companion.WALL_STREET_JOURNAL
import app.android.newsapp.ui.screens.landing.LandingViewModel
import app.android.newsapp.ui.screens.landing.NewsListScreen
import app.android.newsapp.ui.screens.landing.navigation.LandingNavGraph
import app.android.newsapp.ui.theme.BBCNewsTheme
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class LandingScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var viewModel: LandingViewModel

    lateinit var fakeRepository: FakeNewsRepository

    @Before
    fun setup() {
        fakeRepository = FakeNewsRepository()
        viewModel = LandingViewModel(
            newsRepository = fakeRepository,
            eventBus = EventBus()
        )
    }

    @Test
    fun `withSource-techcrunch-Displays-TechCrunch-Title`() = runTest {
        viewModel.setSource(TECH_CRUNCH)
        advanceUntilIdle()
        composeTestRule.setContent {
            BBCNewsTheme {
                NewsListScreen(
                    viewModel = viewModel,
                    hasNetwork = true,
                    navController = rememberNavController()
                )
            }
        }
        val providerName = viewModel.newsState.value.providerName
        assertThat(providerName).isEqualTo("TechCrunch")
        composeTestRule.onNodeWithText(providerName).assertIsDisplayed()
    }

    @Test
    fun `withSource-thewallstreetjournal-Displays-TheWallStreetJournal-Title`() = runTest {
        viewModel.setSource(WALL_STREET_JOURNAL)
        advanceUntilIdle()
        composeTestRule.setContent {
            BBCNewsTheme {
                NewsListScreen(
                    viewModel = viewModel,
                    hasNetwork = true,
                    navController = rememberNavController()
                )
            }
        }
        val providerName = viewModel.newsState.value.providerName
        assertThat(providerName).isEqualTo("The Wall Street Journal")
        composeTestRule.onNodeWithText(providerName).assertIsDisplayed()
    }

    @Test
    fun `withSource-techcrunch-Displays-AllListItems`() = runTest {
        viewModel.setSource(TECH_CRUNCH)
        advanceUntilIdle()
        val state = viewModel.newsState.value
        composeTestRule.setContent {
            BBCNewsTheme {
                NewsListScreen(
                    viewModel = viewModel,
                    hasNetwork = true,
                    navController = rememberNavController()
                )
            }
        }
        state.articles.forEach { item ->
            composeTestRule.onAllNodes(hasText(item.title)).assertAll(hasText(item.title))
        }
    }

    @Test
    fun `withSource-thewallstreetjournal-Displays-AllListItems`() = runTest {
        viewModel.setSource(WALL_STREET_JOURNAL)
        advanceUntilIdle()
        val state = viewModel.newsState.value
        composeTestRule.setContent {
            BBCNewsTheme {
                NewsListScreen(
                    viewModel = viewModel,
                    hasNetwork = true,
                    navController = rememberNavController()
                )
            }
        }
        state.articles.forEach { item ->
            composeTestRule.onAllNodes(hasText(item.title)).assertAll(hasText(item.title))
        }
    }

    @Test
    fun `withSource-techcrunch-ClickOnItemNavigatesToDetailScreen`() = runTest {
        viewModel.setSource(TECH_CRUNCH)
        advanceUntilIdle()
        val item = viewModel.newsState.value
        composeTestRule.setContent {
            BBCNewsTheme {
                LandingNavGraph(viewModel = viewModel, hasNetwork = true)
            }
        }

        composeTestRule.onNodeWithText((item.articles.first().title)).assertIsDisplayed()
            .performClick()
        val selectedArticle = viewModel.newsState.value.selectedArticle
        assertThat(selectedArticle).isNotNull()

        composeTestRule.onNodeWithText(selectedArticle!!.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(selectedArticle.description).assertIsDisplayed()
        composeTestRule.onNodeWithText(selectedArticle.content).assertIsDisplayed()

    }
}