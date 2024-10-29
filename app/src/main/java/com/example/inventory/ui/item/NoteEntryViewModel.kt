// Ubicación: com.example.inventory.ui.notes

package com.example.inventory.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Note
import com.example.inventory.data.NotesRepository
import kotlinx.coroutines.launch

class NoteEntryViewModel(private val notesRepository: NotesRepository) : ViewModel() {
    var noteUiState by mutableStateOf(NoteUiState())
        private set

    fun updateTitle(newTitle: String) {
        noteUiState = noteUiState.copy(title = newTitle)
    }

    fun updateContent(newContent: String) {
        noteUiState = noteUiState.copy(content = newContent)
    }

    fun saveNote() {
        viewModelScope.launch {
            notesRepository.insertNote(Note(
                title = noteUiState.title,
                content = noteUiState.content
            ))
        }
    }
}

data class NoteUiState(
    val title: String = "",
    val content: String = ""
)
