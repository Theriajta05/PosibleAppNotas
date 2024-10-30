package com.example.inventory.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory.data.NotesRepository

class NoteEntryViewModel(
    private val notesRepository: NotesRepository
) : ViewModel() {

    var noteUiState by mutableStateOf(NoteUiState())
        private set

    fun updateTitle(newTitle: String) {
        val updatedNoteDetails = noteUiState.noteDetails.copy(title = newTitle)
        updateUiState(updatedNoteDetails)
    }

    fun updateContent(newContent: String) {
        val updatedNoteDetails = noteUiState.noteDetails.copy(content = newContent)
        updateUiState(updatedNoteDetails)
    }

    private fun updateUiState(noteDetails: NoteDetails) {
        noteUiState = NoteUiState(noteDetails = noteDetails, isEntryValid = validateInput(noteDetails))
    }

    private fun validateInput(uiState: NoteDetails): Boolean {
        return uiState.title.isNotBlank() && uiState.content.isNotBlank()
    }

    fun saveNote() {
        // Lógica para guardar la nota en el repositorio
    }
}

data class NoteUiState(
    val noteDetails: NoteDetails = NoteDetails(),
    val isEntryValid: Boolean = false
)

data class NoteDetails(
    val id: Int = 0,
    val title: String = "",
    val content: String = ""
)
