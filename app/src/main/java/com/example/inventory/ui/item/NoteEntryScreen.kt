// Ubicación: com.example.inventory.ui.notes

package com.example.inventory.ui.notes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.R
import com.example.inventory.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@Composable
fun NoteEntryScreen(
    navigateBack: () -> Unit,
    viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    NoteEntryContent(
        noteUiState = viewModel.noteUiState,
        onSaveClick = {
            coroutineScope.launch {
                viewModel.saveNote()
                navigateBack()
            }
        },
        onTitleChange = { viewModel.updateTitle(it) },
        onContentChange = { viewModel.updateContent(it) }
    )
}

@Composable
fun NoteEntryContent(
    noteUiState: NoteUiState,
    onSaveClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = noteUiState.title,
            onValueChange = onTitleChange,
            label = { Text(stringResource(R.string.note_title)) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = noteUiState.content,
            onValueChange = onContentChange,
            label = { Text(stringResource(R.string.note_content)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onSaveClick) {
            Text(text = stringResource(R.string.save_note))
        }
    }
}
