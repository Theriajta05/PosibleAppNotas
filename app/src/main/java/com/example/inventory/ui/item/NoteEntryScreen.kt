package com.example.inventory.ui.item

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme


object NoteEntryDestination: NavigationDestination{
    override val route = "note_entry"
    override val titleRes = R.string.note_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEntryScreen(
    onNavigateUp:()-> Unit,
    navigateBack: () -> Unit,
    viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val noteUiState by remember { mutableStateOf(viewModel.noteUiState) }

    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(R.string.note_entry_title),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        content = { padding ->
            NoteEntryBody(
                noteUiState = noteUiState,
                onTitleChange = viewModel::updateTitle,
                onContentChange = viewModel::updateContent,
                onSaveClick = {
                    viewModel.saveNote()
                    navigateBack()
                },
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            )
        }
    )
}

