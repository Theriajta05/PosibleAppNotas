// Ubicación: com.example.inventory.ui.navigation

package com.example.inventory.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.inventory.ui.notes.NotesScreen
import com.example.inventory.ui.notes.NoteEntryScreen
import com.example.inventory.ui.notes.NotesDestination

@Composable
fun InventoryNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = NotesDestination.route) {
        composable(NotesDestination.route) {
            NotesScreen(
                navigateToNoteEntry = { navController.navigate("note_entry") },
                navigateToNoteDetail = { id -> navController.navigate("note_detail/$id") }
            )
        }
        composable("note_entry") {
            NoteEntryScreen(navigateBack = { navController.popBackStack() })
        }
    }
}
