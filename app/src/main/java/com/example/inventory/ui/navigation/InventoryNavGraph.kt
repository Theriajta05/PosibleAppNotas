// Ubicación: com.example.inventory.ui.navigation

package com.example.inventory.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventory.ui.notes.NotesScreen
import com.example.inventory.ui.notes.NoteDetailsScreen


@Composable
fun InventoryNavHost(
    navController: NavHostController,
    startDestination: String = "notes"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("notes") {
            NotesScreen(
                navigateToNoteEntry = { navController.navigate("note_entry") },
                navigateToNoteDetail = { noteId ->
                    navController.navigate(NoteDetailsDestination.createRoute(noteId))
                }
            )
        }
        composable(
            route = NoteDetailsDestination.route,
            arguments = listOf(navArgument(NoteDetailsDestination.noteIdArg) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt(NoteDetailsDestination.noteIdArg) ?: 0
            NoteDetailsScreen(noteId = noteId, navigateBack = { navController.popBackStack() })
        }
    }
}
