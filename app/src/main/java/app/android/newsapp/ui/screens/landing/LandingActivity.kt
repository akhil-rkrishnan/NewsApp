package app.android.newsapp.ui.screens.landing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.android.newsapp.ui.theme.BBCNewsTheme

class LandingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BBCNewsTheme {
                NewsListScreen()
            }
        }
    }
}