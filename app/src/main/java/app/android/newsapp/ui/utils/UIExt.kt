package app.android.newsapp.ui.utils

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity

/**
 * Method to start component activity in a context scope
 * @param target [Class] the target to be launched.
 **/
fun Context.startComponentActivity(target: Class<out ComponentActivity>) {
    val intent = Intent(this, target)
    startActivity(intent)
}