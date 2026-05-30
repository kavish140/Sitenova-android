package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.theme.*
import com.example.ui.viewmodel.SiteNovaViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteScreen(
    viewModel: SiteNovaViewModel,
    modifier: Modifier = Modifier
) {
    val savedQuotes by viewModel.savedQuotes.collectAsStateWithLifecycle()
    var expandedDropdown by remember { mutableStateOf(false) }
    val scopes = listOf(
        "Starter Landing (From Rs. 5,000)",
        "Business Discovery Suite (From Rs. 15,000)",
        "Custom Enterprise Integration (From Rs. 40,000)"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Form Title Card block
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Quote Estimator",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.SpacerHeight())
                Text(
                    text = "Determine code scope parameters to compute pricing instantly.",
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Real-Time Dynamic Price Output Panel
        item {
            val livePrice = viewModel.calculateCurrentPrice()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, AccentSky, RoundedCornerShape(20.dp))
                    .background(DarkSurfaceElevated)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ESTIMATED TOTAL ARCHITECTURE COST",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                
                // Big digital pricing display
                Text(
                    text = "Rs. ${java.text.NumberFormat.getNumberInstance(java.util.Locale.US).format(livePrice)}",
                    color = AccentSky,
                    fontWeight = FontWeight.Black,
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Monospace
                )
                
                Spacer(modifier = Modifier.height(6.dp))
                HorizontalDivider(color = BorderSlate.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = null,
                        tint = AccentEmerald,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "99.9% Operational Node Readiness",
                        color = AccentEmerald,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Form Fields (Form Card)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, BorderSlate, RoundedCornerShape(20.dp))
                    .background(DarkSurface)
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Name Input
                Column {
                    Text(
                        text = "YOUR NAME",
                        color = TextPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = viewModel.quoteClientName,
                        onValueChange = { 
                            viewModel.quoteClientName = it
                            viewModel.clearQuoteStatus()
                        },
                        placeholder = { Text("E.g. Dr. Dipti", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentSky,
                            unfocusedBorderColor = BorderSlate,
                            focusedContainerColor = DarkSurfaceElevated,
                            unfocusedContainerColor = DarkSurfaceElevated,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("quote_name_input"),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                }

                // Email Input
                Column {
                    Text(
                        text = "EMAIL ENDPOINT",
                        color = TextPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = viewModel.quoteClientEmail,
                        onValueChange = { 
                            viewModel.quoteClientEmail = it
                            viewModel.clearQuoteStatus()
                        },
                        placeholder = { Text("you@example.com", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentSky,
                            unfocusedBorderColor = BorderSlate,
                            focusedContainerColor = DarkSurfaceElevated,
                            unfocusedContainerColor = DarkSurfaceElevated,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("quote_email_input"),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                }

                // Scope Select Dropdown menu 
                Column {
                    Text(
                        text = "ARCHITECTURAL FRAMEWORK LEVEL",
                        color = TextPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    ExposedDropdownMenuBox(
                        expanded = expandedDropdown,
                        onExpandedChange = { expandedDropdown = !expandedDropdown }
                    ) {
                        OutlinedTextField(
                            value = viewModel.quoteProjectScope,
                            onValueChange = {},
                            readOnly = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AccentSky,
                                unfocusedBorderColor = BorderSlate,
                                focusedContainerColor = DarkSurfaceElevated,
                                unfocusedContainerColor = DarkSurfaceElevated,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = expandedDropdown,
                            onDismissRequest = { expandedDropdown = false },
                            modifier = Modifier.background(DarkSurfaceElevated)
                        ) {
                            scopes.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option, color = TextPrimary) },
                                    onClick = {
                                        viewModel.quoteProjectScope = option
                                        viewModel.clearQuoteStatus()
                                        expandedDropdown = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Extras Checkboxes
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "ADDITIONAL FUNCTIONAL WORKSPACES",
                        color = TextPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // Local Schema SEO toggle card
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(DarkSurfaceElevated)
                            .clickable { viewModel.extraSEO = !viewModel.extraSEO }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = viewModel.extraSEO,
                            onCheckedChange = { viewModel.extraSEO = it },
                            colors = CheckboxDefaults.colors(checkedColor = AccentSky, uncheckedColor = TextMuted)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "Advanced Schema Local SEO Setup (+ Rs. 5,000)", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Boosts regional maps discovery listings", color = TextSecondary, fontSize = 10.sp)
                        }
                    }

                    // CMS toggle card
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(DarkSurfaceElevated)
                            .clickable { viewModel.extraCMS = !viewModel.extraCMS }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = viewModel.extraCMS,
                            onCheckedChange = { viewModel.extraCMS = it },
                            colors = CheckboxDefaults.colors(checkedColor = AccentSky, uncheckedColor = TextMuted)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "Full Blog / Content Management System (+ Rs. 7,000)", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Enables real-time article/asset edits", color = TextSecondary, fontSize = 10.sp)
                        }
                    }

                    // Ecomm toggle card
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(DarkSurfaceElevated)
                            .clickable { viewModel.extraEcommerce = !viewModel.extraEcommerce }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = viewModel.extraEcommerce,
                            onCheckedChange = { viewModel.extraEcommerce = it },
                            colors = CheckboxDefaults.colors(checkedColor = AccentSky, uncheckedColor = TextMuted)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "Merchant Cart / Secure Checkout (+ Rs. 12,000)", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Supports local checkout card payments", color = TextSecondary, fontSize = 10.sp)
                        }
                    }
                }

                // Scope Description
                Column {
                    Text(
                        text = "DELIVERABLE DESCRIPTION",
                        color = TextPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = viewModel.quoteDescription,
                        onValueChange = { 
                            viewModel.quoteDescription = it
                            viewModel.clearQuoteStatus()
                        },
                        placeholder = { Text("Describe your project's specific layouts and design styling requests...", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentSky,
                            unfocusedBorderColor = BorderSlate,
                            focusedContainerColor = DarkSurfaceElevated,
                            unfocusedContainerColor = DarkSurfaceElevated,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .testTag("quote_desc_input"),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 5
                    )
                }

                // Feedback Indicators 
                viewModel.quoteFormError?.let { err ->
                    Text(
                        text = err,
                        color = ErrorRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                viewModel.quoteFormSuccessMessage?.let { success ->
                    Text(
                        text = success,
                        color = AccentEmerald,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // Trigger Button
                Button(
                    onClick = { viewModel.submitQuote() },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryWhite),
                    modifier = Modifier
                        .testTag("quote_transmit_btn")
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Save & Transmit Parameters",
                        color = DarkBackground,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Saved Estimates History Title
        if (savedQuotes.isNotEmpty()) {
            item {
                Text(
                    text = "Saved Layout Estimates",
                    fontWeight = FontWeight.Bold,
                    color = PrimaryWhite,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            // Database items list representation
            items(savedQuotes) { itemQuote ->
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
                            text = itemQuote.clientName,
                            color = PrimaryWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Text(
                            text = itemQuote.projectScope,
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = itemQuote.description,
                            color = TextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Estimated Budget: Rs. ${java.text.NumberFormat.getNumberInstance(java.util.Locale.US).format(itemQuote.calculatedPrice)}",
                            color = AccentSky,
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    IconButton(
                        onClick = { viewModel.deleteQuoteItem(itemQuote.id) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete Quote",
                            tint = ErrorRed,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // Safe spacing for navbar at coordinates base
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
