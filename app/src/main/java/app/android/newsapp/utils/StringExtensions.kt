package app.android.newsapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.android.newsapp.R

@Composable
fun String?.toTitleOrDefault(): String {
    return if (this.isNullOrEmpty())
        stringResource(id = R.string.nullTitle)
    else
        this
}

@Composable
fun String?.toContentOrDefault(): String {
    return if (this.isNullOrEmpty())
        stringResource(id = R.string.nullContent)
    else
        this
}

@Composable
fun String?.toDescriptionOrDefault(): String {
    return if (this.isNullOrEmpty())
        stringResource(id = R.string.nullDescription)
    else
        this
}