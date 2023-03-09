package app.android.newsapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.android.newsapp.R

/**
 * Returns fall back string if the string is null
 * @return string
 **/
@Composable
fun String?.toTitleOrDefault(): String {
    return if (this.isNullOrEmpty())
        stringResource(id = R.string.nullTitle)
    else
        this
}

/**
 * Returns fall back string if the string is null
 * @return string
 **/
@Composable
fun String?.toContentOrDefault(): String {
    return if (this.isNullOrEmpty())
        stringResource(id = R.string.nullContent)
    else
        this
}

/**
 * Returns fall back string if the string is null
 * @return string
 **/
@Composable
fun String?.toDescriptionOrDefault(): String {
    return if (this.isNullOrEmpty())
        stringResource(id = R.string.nullDescription)
    else
        this
}