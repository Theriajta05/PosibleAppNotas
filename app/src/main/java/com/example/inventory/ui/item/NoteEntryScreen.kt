package com.example.inventory.ui.item

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.ad_coding.noteappcourse.componentes.MultimediaPicker
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.theme.InventoryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEntryScreen(
    navigateBack: () -> Unit,
    viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val noteUiState by viewModel.noteUiState.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf("") }  // Declara la variable para la fecha seleccionada

    var showTimePicker by remember { mutableStateOf(false) }  // Nueva variable para mostrar el reloj
    val selectedTime = remember { mutableStateOf("") }  // Hora seleccionada

    var showMultimediaPicker by remember { mutableStateOf(false) } // Mostrar el MultimediaPicker
    val multimediaUris = remember { mutableStateOf<List<String>>(listOf()) } // Almacenar las URIs seleccionadas

    var showAudioRecorder by remember { mutableStateOf(false) }  // Control para mostrar el grabador de audio
    var showCameraButton by remember { mutableStateOf(false) } // Control para mostrar la cámara

    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(R.string.note_entry_title),
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
                OutlinedTextField(
                    value = noteUiState.noteDetails?.title ?: "", // Manejo de nulos
                    onValueChange = { newTitle: String -> viewModel.updateTitle(newTitle) },
                    label = { Text("Title") },
                    modifier = Modifier.padding(16.dp)
                )
                OutlinedTextField(
                    value = noteUiState.noteDetails?.content ?: "", // Manejo de nulos
                    onValueChange = { newContent: String -> viewModel.updateContent(newContent) },
                    label = { Text("Content") },
                    modifier = Modifier.padding(16.dp)
                )

                // Botón para abrir la cámara
                Button(
                    onClick = {
                        showCameraButton = true
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Abrir cámara",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Abrir Cámara")
                }
                // Botón para abrir el MultimediaPicker
                Button(
                    onClick = { showMultimediaPicker = true },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        imageVector = Icons.Default.MailOutline,
                        contentDescription = "Abrir selector de multimedia"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Añadir Multimedia")
                }

                // Mostrar la lista de multimedia seleccionada
                LazyColumn(
                    modifier = Modifier
                        .height(150.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    items(multimediaUris.value) { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(model = Uri.parse(uri)),
                            contentDescription = null,
                            modifier = Modifier
                                .width(100.dp)
                                .height(150.dp)
                                .padding(8.dp)
                        )
                    }
                }


                // Agregar botón de calendario
                IconButton(
                    onClick = {
                        // Acción para abrir el calendario
                        showDatePicker = true
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,  // Ícono de calendario
                        contentDescription = "Abrir calendario",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                // Mostrar la fecha seleccionada
                Text(
                    text = "Fecha seleccionada: ${selectedDate.value}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )

                // Agregar botón de reloj
                IconButton(
                    onClick = {
                        // Acción para abrir el reloj
                        showTimePicker = true
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,  // Ícono de reloj
                        contentDescription = "Abrir reloj",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Mostrar la hora seleccionada
                Text(
                    text = "Hora seleccionada: ${selectedTime.value}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )

                // Nuevo botón para abrir el grabador de audio
                Button(
                    onClick = {
                        showAudioRecorder = true
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Abrir grabador de audio",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Abrir Grabador de Audio")
                }



                Button(
                    onClick = {
                        viewModel.saveNote()
                        navigateBack()
                    },
                    enabled = noteUiState.noteDetails?.isEntryValid() == true, // Validar entrada
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Save Note")
                }
            }
        }
    )

    if (showCameraButton) {
        AlertDialog(
            onDismissRequest = { showCameraButton = false },
            title = { Text("Cámara") },
            text = {
                Column {
                    Text("Toma fotos usando el botón de la cámara.")
                    Spacer(modifier = Modifier.height(16.dp))
                    CameraButton()
                }
            },
            confirmButton = {
                Button(onClick = { showCameraButton = false }) {
                    Text("Cerrar")
                }
            }
        )
    }



    if (showAudioRecorder) {
        AlertDialog(
            onDismissRequest = { showAudioRecorder = false },
            title = { Text("Grabador de Audio") },
            text = {
                Column {
                    Text("Usa los botones para grabar o reproducir audio.")
                    Spacer(modifier = Modifier.height(16.dp))
                    AudioRecorderButton()
                }
            },
            confirmButton = {
                Button(onClick = { showAudioRecorder = false }) {
                    Text("Cerrar")
                }
            }
        )
    }


    // Mostrar el DatePickerDialog cuando showDatePicker es verdadero
    if (showDatePicker) {
        val context = LocalContext.current
        val currentDate = java.util.Calendar.getInstance()
        val year = currentDate.get(java.util.Calendar.YEAR)
        val month = currentDate.get(java.util.Calendar.MONTH)
        val day = currentDate.get(java.util.Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val formattedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"

                // Actualizar la variable selectedDate
                selectedDate.value = formattedDate

                // Mostrar un mensaje con la fecha seleccionada
                Toast.makeText(context, "Fecha seleccionada: $formattedDate", Toast.LENGTH_SHORT).show()

                // Cerrar el diálogo
                showDatePicker = false
            },
            year,
            month,
            day
        ).show()
    }
    // Mostrar el TimePickerDialog cuando showTimePicker es verdadero
    if (showTimePicker) {
        val context = LocalContext.current
        val currentTime = java.util.Calendar.getInstance()
        val hour = currentTime.get(java.util.Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(java.util.Calendar.MINUTE)

        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                val formattedTime = "$selectedHour:${String.format("%02d", selectedMinute)}"

                // Actualizar la variable selectedTime
                selectedTime.value = formattedTime

                // Mostrar un mensaje con la hora seleccionada
                Toast.makeText(context, "Hora seleccionada: $formattedTime", Toast.LENGTH_SHORT).show()

                // Cerrar el diálogo
                showTimePicker = false
            },
            hour,
            minute,
            true // 24 horas
        ).show()
    }
    if (showMultimediaPicker) {
        AlertDialog(
            onDismissRequest = { showMultimediaPicker = false },
            title = { Text("Seleccionar Multimedia") },
            text = {
                MultimediaPicker { selectedUris ->
                    multimediaUris.value = selectedUris
                    viewModel.updateMultimediaUris(selectedUris) // Actualizar en el ViewModel
                }
            },
            confirmButton = {
                Button(onClick = { showMultimediaPicker = false }) {
                    Text("Cerrar")
                }
            }
        )
    }
}



