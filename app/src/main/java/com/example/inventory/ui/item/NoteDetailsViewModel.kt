// Ubicación: com.example.inventory.ui.notes

package com.example.inventory.ui.notes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Note
import com.example.inventory.data.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve, update, and delete a note from the [NotesRepository]'s data source.
 */
class NoteDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val noteId: Int = checkNotNull(savedStateHandle[NoteDetailsDestination.noteIdArg])

    private val _noteDetails = MutableStateFlow<Note?>(null)
    val noteDetails: StateFlow<Note?> = _noteDetails

    init {
        getNoteById(noteId)
    }

    private fun getNoteById(id: Int) {
        viewModelScope.launch {
            notesRepository.getNoteStream(id).collect { note ->
                _noteDetails.value = note
            }
        }
    }

    fun deleteNote() {
        _noteDetails.value?.let { note ->
            viewModelScope.launch {
                notesRepository.deleteNote(note)
            }
        }
    }
}
