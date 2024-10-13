package com.cb.meapps.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.meapps.domain.usecase.GetAllDocumentsUseCase
import com.cb.meapps.domain.model.Document
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BureaucraticDocsViewModel @Inject constructor(
    private val getAllDocumentsUseCase: GetAllDocumentsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DocsState())
    val state: StateFlow<DocsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getAllDocumentsUseCase().collect { documents ->
                _state.update { currentState ->
                    currentState.copy(
                        documents = documents
                    )
                }
            }
        }
    }

    fun dispatch(action: BureaucraticDocsAction) {

    }


}

data class DocsState(
    val documents: List<Document> = emptyList()
)

sealed class BureaucraticDocsAction {

}