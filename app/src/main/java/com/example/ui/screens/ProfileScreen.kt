package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.theme.*
import com.example.ui.viewmodel.SiteNovaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: SiteNovaViewModel,
    onLogout: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val savedCalls by viewModel.savedBookedCalls.collectAsStateWithLifecycle()

    // Mock dates for selectable calendar carousel 
    val calendarDates = remember {
        listOf(
            CalendarOption("Tue, May 26", "2026-05-26"),
            CalendarOption("Wed, May 27", "2026-05-27"),
            CalendarOption("Thu, May 28", "2026-05-28"),
            CalendarOption("Fri, May 29", "2026-05-29"),
            CalendarOption("Sat, May 30", "2026-05-30")
        )
    }

    // Selectable Timeslots matching agency client timings
    val timeSlots = remember {
        listOf("10:00 AM", "11:30 AM", "02:00 PM", "04:30 PM", "06:00 PM")
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Agency node details banner block
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Agency Hub & Scheduler",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.SpacerHeight())
                Text(
                    text = "Operational focal nodes and interactive consultation booker.",
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // 1. Coordinates, Location & Working Nodes Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, BorderSlate, RoundedCornerShape(20.dp))
                    .background(DarkSurface)
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(DarkSurfaceElevated),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Place, contentDescription = "Location", tint = AccentSky)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = "HQ Coordination Node", color = PrimaryWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = "Mulund, Mumbai, IN (All Nodes Operational)", color = TextSecondary, fontSize = 12.sp)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(DarkSurfaceElevated),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Email, contentDescription = "Email", tint = AccentSky)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = "Broadcasting Hub Address", color = PrimaryWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = "kavishganatra5@gmail.com", color = TextSecondary, fontSize = 12.sp)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(DarkSurfaceElevated),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.TrendingUp, contentDescription = "SLA", tint = AccentSky)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = "Guaranteed Design Service Level SLA", color = PrimaryWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = "100% Core Web Vitals | Responsive Jamstack Codebase", color = TextSecondary, fontSize = 12.sp)
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorRed),
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Sign Out", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        // 2. Interactive Call Booking Calendar (reminiscent of BookCallWidget.tsx)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, BorderSlate, RoundedCornerShape(20.dp))
                    .background(DarkSurface)
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null, tint = AccentSky, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Interactive Calendar client", color = PrimaryWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(14.dp))

                // Selectable Date via DatePicker
                Text(
                    text = "SELECT BOOKING DATE",
                    color = TextSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                var showDatePicker by remember { mutableStateOf(false) }
                val datePickerState = rememberDatePickerState()
                
                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US)
                                    viewModel.bookingDate = sdf.format(java.util.Date(millis))
                                    viewModel.clearBookingStatus()
                                }
                                showDatePicker = false
                            }) {
                                Text("OK", color = AccentSky)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = false }) {
                                Text("Cancel", color = TextSecondary)
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
                
                Button(
                    onClick = { showDatePicker = true },
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceElevated),
                    border = BorderStroke(1.dp, BorderSlate),
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (viewModel.bookingDate.isNotEmpty()) "Selected: ${viewModel.bookingDate}" else "Choose Date",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Selectable Timeslots Carousel
                Text(
                    text = "SELECT TIMESLOT",
                    color = TextSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(timeSlots) { slot ->
                        val isSelected = viewModel.bookingTimeSlot == slot
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) AccentEmerald else DarkSurfaceElevated)
                                .border(1.dp, if (isSelected) AccentEmerald else BorderSlate, RoundedCornerShape(10.dp))
                                .clickable {
                                    viewModel.bookingTimeSlot = slot
                                    viewModel.clearBookingStatus()
                                }
                                .padding(horizontal = 12.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = slot,
                                color = if (isSelected) DarkBackground else TextSecondary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                HorizontalDivider(color = BorderSlate.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(14.dp))

                // Booking Input details fields
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Name
                    OutlinedTextField(
                        value = viewModel.bookingClientName,
                        onValueChange = { 
                            viewModel.bookingClientName = it
                            viewModel.clearBookingStatus()
                        },
                        placeholder = { Text("Client Name", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = AccentSky,
                            unfocusedBorderColor = BorderSlate,
                            focusedContainerColor = DarkSurfaceElevated,
                            unfocusedContainerColor = DarkSurfaceElevated
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("booking_name_input"),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    // Email
                    OutlinedTextField(
                        value = viewModel.bookingClientEmail,
                        onValueChange = { 
                            viewModel.bookingClientEmail = it
                            viewModel.clearBookingStatus()
                        },
                        placeholder = { Text("Contact Email", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = AccentSky,
                            unfocusedBorderColor = BorderSlate,
                            focusedContainerColor = DarkSurfaceElevated,
                            unfocusedContainerColor = DarkSurfaceElevated
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("booking_email_input"),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    // Phone Contacts
                    OutlinedTextField(
                        value = viewModel.bookingPhone,
                        onValueChange = { 
                            viewModel.bookingPhone = it
                            viewModel.clearBookingStatus()
                        },
                        placeholder = { Text("Phone Number Node", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = AccentSky,
                            unfocusedBorderColor = BorderSlate,
                            focusedContainerColor = DarkSurfaceElevated,
                            unfocusedContainerColor = DarkSurfaceElevated
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("booking_phone_input"),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    // Special project requirement note
                    OutlinedTextField(
                        value = viewModel.bookingNotes,
                        onValueChange = { 
                            viewModel.bookingNotes = it
                            viewModel.clearBookingStatus()
                        },
                        placeholder = { Text("Notes regarding specific objectives...", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = AccentSky,
                            unfocusedBorderColor = BorderSlate,
                            focusedContainerColor = DarkSurfaceElevated,
                            unfocusedContainerColor = DarkSurfaceElevated
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .testTag("booking_notes_input"),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                viewModel.bookingFormError?.let { err ->
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = err, color = ErrorRed, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                viewModel.bookingFormSuccessMessage?.let { msg ->
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = msg, color = AccentEmerald, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.submitBooking { name, email, date, time ->
                            try {
                                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:")
                                    putExtra(Intent.EXTRA_EMAIL, arrayOf("kavishganatra5@gmail.com", email))
                                    putExtra(Intent.EXTRA_SUBJECT, "Consultation Booked: SiteNova & $name")
                                    putExtra(Intent.EXTRA_TEXT, "Hello $name,\n\nYour consultation call with SiteNova has been booked.\n\nDate: $date\nTime: $time\n\nLooking forward to speaking with you on Google Meet!\n\nBest,\nKavish Ganatra, SiteNova")
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                }
                                val calendarIntent = Intent(Intent.ACTION_INSERT).apply {
                                    data = CalendarContract.Events.CONTENT_URI
                                    putExtra(CalendarContract.Events.TITLE, "SiteNova Consultation: $name")
                                    putExtra(CalendarContract.Events.DESCRIPTION, "Project Consultation Call with SiteNova. Scheduled for $date at $time")
                                    putExtra(Intent.EXTRA_EMAIL, "kavishganatra5@gmail.com, $email")
                                    putExtra(CalendarContract.Events.EVENT_LOCATION, "Google Meet")
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                }
                                try {
                                    context.startActivity(emailIntent)
                                } catch (e: Exception) {
                                    android.widget.Toast.makeText(context, "Mailing requires backend/API! Saved locally.", android.widget.Toast.LENGTH_LONG).show()
                                }
                                try {
                                    context.startActivity(calendarIntent)
                                } catch (e: Exception) {
                                    // Ignore calendar crash
                                }
                                
                                // Trigger Local Notification
                                val notificationManager = context.getSystemService(android.content.Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
                                val channelId = "booking_channel"
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    val channel = android.app.NotificationChannel(channelId, "Bookings", android.app.NotificationManager.IMPORTANCE_HIGH)
                                    notificationManager.createNotificationChannel(channel)
                                }
                                val builder = androidx.core.app.NotificationCompat.Builder(context, channelId)
                                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                                    .setContentTitle("Meeting Scheduled!")
                                    .setContentText("Consultation booked on $date at $time")
                                    .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
                                    .setAutoCancel(true)
                                notificationManager.notify(1003, builder.build())
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryWhite),
                    modifier = Modifier
                        .testTag("book_call_trigger_btn")
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Schedule Consultation Call", color = DarkBackground, fontWeight = FontWeight.Bold)
                }
            }
        }

        // 3. Saved Consultation Bookings List
        if (savedCalls.isNotEmpty()) {
            item {
                Text(
                    text = "Active Calendar Bookings",
                    color = PrimaryWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            items(savedCalls) { call ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, BorderSlate, RoundedCornerShape(16.dp))
                        .background(DarkSurface)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Call with ${call.clientName}",
                            color = PrimaryWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Event, contentDescription = null, tint = AccentSky, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "${call.bookingDate} at ${call.timeSlot}", color = TextSecondary, fontSize = 12.sp)
                        }
                        if (call.messageNote.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Note: ${call.messageNote}", color = TextMuted, fontSize = 11.sp, lineHeight = 15.sp)
                        }
                    }

                    IconButton(
                        onClick = { viewModel.deleteBookedCallItem(call.id) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Cancel,
                            contentDescription = "Cancel booking",
                            tint = ErrorRed,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // Buffer spacer for safe scrolling
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

data class CalendarOption(val label: String, val value: String)
