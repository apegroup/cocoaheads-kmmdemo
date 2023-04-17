package com.umain.cocoaheads

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
internal fun App(viewModel: MainViewModel = MainViewModel()) = AppTheme {
    val platformGreet = remember { Greeting(getPlatform()).greeting() }

    val uiState = viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        LaunchedEffect(uiState) {
            viewModel.observeViewModel()
            delay(5000)
        }
        LazyColumn {
            itemsIndexed(uiState.value.users.orEmpty()) { index, user ->
                Text(
                    text = "#$index - Hello, ${user?.firstName}  ${user?.lastName}",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }

        Text(
            text = platformGreet,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(16.dp),
        )

        Text(
            text = "Location: ${uiState.value.location?.city} ${uiState.value.location?.countryName}",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(16.dp),
        )
    }
}

internal expect fun openUrl(url: String?)
