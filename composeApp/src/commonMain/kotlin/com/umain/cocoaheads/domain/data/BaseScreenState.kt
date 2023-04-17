package com.umain.cocoaheads.domain.data

abstract class BaseScreenState {
    abstract var message: String?
    abstract var loading: Boolean
}

sealed class UiState {
    object Loading : UiState()
    data class Success(
        val list: List<Any>,
    ) : UiState()

    data class Error(val message: String) : UiState()
}
