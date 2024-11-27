package com.example.inventory.ui.item

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.ui.AppViewModelProvider
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEntryScreen(
    navigateBack: () -> Unit,
    viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val noteUiState by viewModel.noteUiState.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showMultimediaPicker by remember { mutableStateOf(false) }
    var showCameraDialog by remember { mutableStateOf(false) }
    var showAudioRecorderDialog by remember { mutableStateOf(false) }
    var multimediaUris by remember { mutableStateOf<List<String>>(listOf()) }

    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = "Add Note",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                // Title Input
                OutlinedTextField(
                    value = noteUiState.noteDetails?.title.orEmpty(),
                    onValueChange = { viewModel.updateTitle(it) },
                    label = { Text("Title") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )

                // Content Input
                OutlinedTextField(
                    value = noteUiState.noteDetails?.content.orEmpty(),
                    onValueChange = { viewModel.updateContent(it) },
                    label = { Text("Content") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )

                // Select Date
                Button(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Select Date")
                }
                Text(
                    text = "Selected Date: ${
                        noteUiState.noteDetails?.fecha?.takeIf { it != 0L }?.let {
                            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
                        } ?: "Not selected"
                    }",
                    modifier = Modifier.padding(start = 16.dp)
                )

                // Select Time
                Button(
                    onClick = { showTimePicker = true },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Select Time")
                }
                Text(
                    text = "Selected Time: ${
                        noteUiState.noteDetails?.hora?.takeIf { it != 0L }?.let {
                            SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(it))
                        } ?: "Not selected"
                    }",
                    modifier = Modifier.padding(start = 16.dp)
                )

                // Camera Button
                Button(
                    onClick = { showCameraDialog = true },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Open Camera")
                }

                // Audio Recorder Button
                Button(
                    onClick = { showAudioRecorderDialog = true },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Record Audio")
                }

                // Add Multimedia
                Button(
                    onClick = { showMultimediaPicker = true },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Add Multimedia")
                }
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .height(150.dp)
                ) {
                    items(multimediaUris) { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(model = Uri.parse(uri)),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(8.dp)
                                .width(100.dp)
                                .height(150.dp)
                        )
                    }
                }

                // Save Button
                Button(
                    onClick = {
                        viewModel.saveNote()
                        navigateBack()
                    },
                    enabled = noteUiState.isEntryValid,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Save Note")
                }
            }
        }
    )

    // Date Picker Dialog
    if (showDatePicker) {
        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, day ->
                calendar.set(year, month, day)
                viewModel.updateFecha(calendar.timeInMillis)
                showDatePicker = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Time Picker Dialog
    if (showTimePicker) {
        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            context,
            { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                viewModel.updateHora(calendar.timeInMillis)
                showTimePicker = false
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    // Multimedia Picker Dialog
    if (showMultimediaPicker) {
        AlertDialog(
            onDismissRequest = { showMultimediaPicker = false },
            title = { Text("Select Multimedia") },
            text = {
                MultimediaPicker { selectedUris ->
                    multimediaUris = selectedUris
                    viewModel.updateMultimediaUris(selectedUris)
                }
            },
            confirmButton = {
                Button(onClick = { showMultimediaPicker = false }) {
                    Text("Close")
                }
            }
        )
    }

    // Camera Dialog
    if (showCameraDialog) {
        AlertDialog(
            onDismissRequest = { showCameraDialog = false },
            title = { Text("Camera") },
            text = {
                CameraButton()
            },
            confirmButton = {
                Button(onClick = { showCameraDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    // Audio Recorder Dialog
    if (showAudioRecorderDialog) {
        AlertDialog(
            onDismissRequest = { showAudioRecorderDialog = false },
            title = { Text("Audio Recorder") },
            text = {
                AudioRecorderButton()
            },
            confirmButton = {
                Button(onClick = { showAudioRecorderDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}
