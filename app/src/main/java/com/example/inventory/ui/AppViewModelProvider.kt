package com.example.inventory.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory.InventoryApplication
import com.example.inventory.ui.notes.NotesViewModel
import com.example.inventory.ui.notes.NoteEntryViewModel

/**
 * Provides Factory to create instances of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for NoteEntryViewModel
        initializer {
            NoteEntryViewModel(inventoryApplication().container.notesRepository)
        }

        // Initializer for NotesViewModel
        initializer {
            NotesViewModel(inventoryApplication().container.notesRepository)
        }
    }
}

/**
 * Extension function to obtain the [InventoryApplication] instance from the [Application].
 */
fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)
