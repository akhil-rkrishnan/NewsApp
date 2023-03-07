package app.android.newsapp.ui.utils

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity

fun Context.startComponentActivity(target: Class<out ComponentActivity>) {
    val intent = Intent(this, target)
    startActivity(intent)
}