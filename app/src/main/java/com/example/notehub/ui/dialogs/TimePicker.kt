package com.example.notehub.ui.dialogs

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.notehub.constants.MAIN_HORIZONTAL_PADDING
import com.example.notehub.utils.NotificationUtils
import com.vsnappy1.datepicker.DatePicker
import com.vsnappy1.datepicker.data.model.DatePickerDate
import com.vsnappy1.datepicker.data.model.SelectionLimiter
import com.vsnappy1.datepicker.ui.model.DatePickerConfiguration
import com.vsnappy1.timepicker.TimePicker
import com.vsnappy1.timepicker.data.model.TimePickerTime
import com.vsnappy1.timepicker.ui.model.TimePickerConfiguration
import kotlinx.coroutines.launch


@Composable
fun DateTimePickerDialog(
    name: String,
    dismiss: () -> Unit
) {
    var isDateSelected by remember { mutableStateOf(false) }
    var isTimeSelected by remember { mutableStateOf(false) }
    var remainderDateTime by remember { mutableStateOf<Calendar>(Calendar.getInstance()) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(vertical = 24.dp, horizontal = MAIN_HORIZONTAL_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (!isDateSelected){
            DatePickerDialog {
                isDateSelected = true
                remainderDateTime.set(Calendar.YEAR, it.get(Calendar.YEAR))
                remainderDateTime.set(Calendar.MONTH, it.get(Calendar.MONTH))
                remainderDateTime.set(Calendar.DAY_OF_MONTH, it.get(Calendar.DAY_OF_MONTH))
            }
        }
        if(!isTimeSelected && isDateSelected){
            TimePickerDialog {
                isTimeSelected = true
                remainderDateTime.set(Calendar.HOUR_OF_DAY, it.get(Calendar.HOUR_OF_DAY))
                remainderDateTime.set(Calendar.MINUTE, it.get(Calendar.MINUTE))
                remainderDateTime.set(Calendar.SECOND, 0)
                NotificationUtils.setReminder(context, remainderDateTime, name)
            }
        }
        if (isDateSelected && isTimeSelected){
            dismiss()
        }
    }
}

@Composable
fun TimePickerDialog(
    onTimeSelected: (Calendar) -> Unit
){
    val currentTime = Calendar.getInstance()
    val reminderTime = remember { Calendar.getInstance() }

    TimePicker(
        onTimeSelected = { hour, minute ->
            reminderTime.set(Calendar.HOUR_OF_DAY, hour)
            reminderTime.set(Calendar.MINUTE, minute)
            reminderTime.set(Calendar.SECOND, 0)
        },
        is24Hour = true,
        time = TimePickerTime(
            hour = currentTime.get(Calendar.HOUR_OF_DAY),
            minute = currentTime.get(Calendar.MINUTE),
        ),
        configuration = TimePickerConfiguration.Builder()
            .selectedTimeTextStyle(
                TextStyle(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            .timeTextStyle(
                TextStyle(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            .build(),
    )
    Button(onClick = { onTimeSelected(reminderTime) }) {
        Text("Confirm")
    }
}

@Composable
fun DatePickerDialog(
    onDateSelected: (Calendar) -> Unit
){
    val currentDate = Calendar.getInstance()
    val reminderDate = remember { Calendar.getInstance() }

    val fromDate = DatePickerDate(
        year = currentDate.get(Calendar.YEAR),
        month = currentDate.get(Calendar.MONTH),
        day = currentDate.get(Calendar.DAY_OF_MONTH)
    )

    DatePicker(
        onDateSelected = { year, month, day ->
            reminderDate.set(Calendar.YEAR, year)
            reminderDate.set(Calendar.MONTH, month)
            reminderDate.set(Calendar.DAY_OF_MONTH, day)
        },
        selectionLimiter = SelectionLimiter(
            fromDate = fromDate
        ),
        configuration = DatePickerConfiguration.Builder()
            .dateTextStyle(
                TextStyle(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            .headerArrowColor(MaterialTheme.colorScheme.primary)
            .headerTextStyle(TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            ))
            .selectedDateBackgroundColor(MaterialTheme.colorScheme.primary)
            .disabledDateColor(Color(0xFF636363))
            .build()
    )
    Button(onClick = { onDateSelected(reminderDate) }) {
        Text("Confirm")
    }
}
