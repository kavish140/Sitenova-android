package com.example.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.BookedCall
import com.example.data.model.EstimateQuote
import com.example.data.repository.SiteNovaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SiteNovaViewModel(private val repository: SiteNovaRepository) : ViewModel() {

    // Quote Input form state variables
    var quoteClientName by mutableStateOf("")
    var quoteClientEmail by mutableStateOf("")
    var quoteProjectScope by mutableStateOf("Starter Landing (From Rs. 5,000)")
    var quoteDescription by mutableStateOf("")
    
    // Dynamic Quote Calculator Extras
    var extraSEO by mutableStateOf(false)
    var extraCMS by mutableStateOf(false)
    var extraEcommerce by mutableStateOf(false)
    var extraResponsivePill by mutableStateOf(true)

    // Validation or submission state helper
    var quoteFormError by mutableStateOf<String?>(null)
    var quoteFormSuccessMessage by mutableStateOf<String?>(null)

    // Call Booking Form State
    var bookingClientName by mutableStateOf("")
    var bookingClientEmail by mutableStateOf("")
    var bookingPhone by mutableStateOf("")
    var bookingDate by mutableStateOf("2026-05-26")
    var bookingTimeSlot by mutableStateOf("10:00 AM - 11:00 AM")
    var bookingNotes by mutableStateOf("")

    // Call Booking validation state helper
    var bookingFormError by mutableStateOf<String?>(null)
    var bookingFormSuccessMessage by mutableStateOf<String?>(null)

    // Global Error Handler boundary triggers (reminiscent of ErrorBoundary.tsx)
    var globalAppError by mutableStateOf<String?>(null)

    // Flows for persisted lists
    val savedQuotes: StateFlow<List<EstimateQuote>> = repository.allQuotes
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val savedBookedCalls: StateFlow<List<BookedCall>> = repository.allBookedCalls
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Dynamic Price Calculation logic
    fun calculateCurrentPrice(): Int {
        var basePrice = when {
            quoteProjectScope.contains("Starter") -> 5000
            quoteProjectScope.contains("Business") -> 15000
            else -> 40000
        }
        if (extraSEO) basePrice += 5000
        if (extraCMS) basePrice += 7000
        if (extraEcommerce) basePrice += 12000
        return basePrice
    }

    // Interactive Submit handles
    fun submitQuote() {
        if (quoteClientName.trim().isEmpty()) {
            quoteFormError = "Please supply your name so we can reference you."
            return
        }
        if (quoteClientEmail.trim().isEmpty() || !quoteClientEmail.contains("@")) {
            quoteFormError = "A valid contact email endpoint is required."
            return
        }
        if (quoteDescription.trim().length < 10) {
            quoteFormError = "Please describe your delivery objectives in at least 10 characters."
            return
        }

        quoteFormError = null
        val calculatedPrice = calculateCurrentPrice()

        viewModelScope.launch {
            try {
                val quote = EstimateQuote(
                    clientName = quoteClientName.trim(),
                    clientEmail = quoteClientEmail.trim(),
                    projectScope = quoteProjectScope,
                    description = quoteDescription.trim(),
                    calculatedPrice = calculatedPrice
                )
                repository.saveQuote(quote)
                quoteFormSuccessMessage = "Parameters successfully transmitted to database! Estimated Quote: Rs. $calculatedPrice"
                
                sendTeamEmailWithEmailJs(
                    clientName = quoteClientName.trim(),
                    clientEmail = quoteClientEmail.trim(),
                    subject = "New Quote Request: $quoteProjectScope",
                    message = "A new quote has been requested. Price: $calculatedPrice.\nDescription: ${quoteDescription.trim()}"
                )

                sendCustomerEmailWithEmailJs(
                    clientName = quoteClientName.trim(),
                    clientEmail = quoteClientEmail.trim(),
                    subject = "Your Quote Request Received: $quoteProjectScope",
                    message = "Thank you for reaching out to us for a quote.\n\nDescription: ${quoteDescription.trim()}\n\nEstimated Price: Rs. $calculatedPrice\n\nWe will review your request and get back to you shortly.\n\nBest regards,\nSiteNova Team"
                )

                // Reset Fields on Success
                quoteClientName = ""
                quoteClientEmail = ""
                quoteDescription = ""
                extraSEO = false
                extraCMS = false
                extraEcommerce = false
            } catch (e: Exception) {
                globalAppError = "Database Transmission Exception: ${e.message}"
            }
        }
    }

    fun submitBooking(onSuccess: (String, String, String, String) -> Unit) {
        if (bookingClientName.trim().isEmpty()) {
            bookingFormError = "Name cannot be left empty."
            return
        }
        if (bookingClientEmail.trim().isEmpty() || !bookingClientEmail.contains("@")) {
            bookingFormError = "Email format appears incorrect."
            return
        }
        if (bookingPhone.trim().length < 8) {
            bookingFormError = "Please specify a valid phone contact node."
            return
        }

        bookingFormError = null

        val name = bookingClientName.trim()
        val email = bookingClientEmail.trim()
        val date = bookingDate
        val time = bookingTimeSlot

        viewModelScope.launch {
            try {
                val call = BookedCall(
                    clientName = name,
                    clientEmail = email,
                    phoneNumber = bookingPhone.trim(),
                    bookingDate = date,
                    timeSlot = time,
                    messageNote = bookingNotes.trim()
                )
                repository.saveBookedCall(call)
                bookingFormSuccessMessage = "Call scheduled! We'll reach out on $date at $time."

                sendTeamEmailWithEmailJs(
                    clientName = name,
                    clientEmail = email,
                    subject = "New Call Booking",
                    message = "A new call has been booked by $name on $date at $time.\nPhone: ${bookingPhone.trim()}\nNotes: ${bookingNotes.trim()}"
                )

                sendCustomerEmailWithEmailJs(
                    clientName = name,
                    clientEmail = email,
                    subject = "Your Call Booking Confirmation",
                    message = "Thank you for booking a call with us.\n\nDate: $date\nTime: $time\nPhone: ${bookingPhone.trim()}\n\nWe will reach out to you at the scheduled time.\n\nBest regards,\nSiteNova Team"
                )

                // Trigger Intents for scheduling and email
                onSuccess(name, email, date, time)

                // Reset Fields
                bookingClientName = ""
                bookingClientEmail = ""
                bookingPhone = ""
                bookingNotes = ""
            } catch (e: Exception) {
                globalAppError = "Database Booking Exception: ${e.message}"
            }
        }
    }

    fun deleteQuoteItem(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteQuote(id)
            } catch (e: Exception) {
                globalAppError = "Delete Quote Exception: ${e.message}"
            }
        }
    }

    fun deleteBookedCallItem(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteBookedCall(id)
            } catch (e: Exception) {
                globalAppError = "Delete Call Exception: ${e.message}"
            }
        }
    }

    fun dismissGlobalError() {
        globalAppError = null
    }

    fun clearQuoteStatus() {
        quoteFormSuccessMessage = null
        quoteFormError = null
    }

    fun clearBookingStatus() {
        bookingFormSuccessMessage = null
        bookingFormError = null
    }

    // --- Authentication ---
    var authError by mutableStateOf<String?>(null)
    var isAuthenticating by mutableStateOf(false)

    fun authenticateWithGoogle(idToken: String, onSuccess: () -> Unit) {
        authError = null
        isAuthenticating = true
        viewModelScope.launch {
            try {
                val api = com.example.data.api.SupabaseNetworkClient.authApi
                val request = com.example.data.api.IdTokenRequest(idToken = idToken)
                api.signInWithIdToken(request = request)
                isAuthenticating = false
                onSuccess()
            } catch (e: Exception) {
                isAuthenticating = false
                authError = "Google Authentication failed: ${e.message}"
            }
        }
    }

    fun authenticateWithSupabase(email: String, pass: String, isSignUp: Boolean, onSuccess: () -> Unit) {
        if (email.isBlank() || pass.isBlank()) {
            authError = "Please enter both email and password."
            return
        }
        authError = null
        isAuthenticating = true
        viewModelScope.launch {
            try {
                val api = com.example.data.api.SupabaseNetworkClient.authApi
                val request = com.example.data.api.AuthRequest(email.trim(), pass)
                if (isSignUp) {
                    api.signUp(request)
                } else {
                    api.signIn(request = request)
                }
                isAuthenticating = false
                onSuccess()
            } catch (e: Exception) {
                isAuthenticating = false
                authError = "Authentication failed: ${e.message}"
            }
        }
    }

    private fun sendTeamEmailWithEmailJs(clientName: String, clientEmail: String, subject: String, message: String) {
        viewModelScope.launch {
            try {
                val serviceId = com.example.BuildConfig.EMAILJS_SERVICE_ID
                val templateId = com.example.BuildConfig.EMAILJS_TEMPLATE_ID
                val userId = com.example.BuildConfig.EMAILJS_USER_ID
                
                if (serviceId.isBlank() || templateId.isBlank() || userId.isBlank()) {
                    return@launch
                }
                
                // For the team template (as per your screenshot):
                // to_name = "SiteNova Team"
                // from_name = clientName
                // reply_to = clientEmail
                // subject = subject
                // message = message
                
                val params = mapOf(
                    "to_name" to "SiteNova Team",
                    "from_name" to clientName,
                    "reply_to" to clientEmail,
                    "subject" to subject,
                    "message" to message
                )
                
                val request = com.example.data.api.EmailJsRequest(
                    serviceId = serviceId,
                    templateId = templateId,
                    userId = userId,
                    templateParams = params
                )
                
                com.example.data.api.EmailJsNetworkClient.emailJsApi.sendEmail(request)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun sendCustomerEmailWithEmailJs(clientName: String, clientEmail: String, subject: String, message: String) {
        viewModelScope.launch {
            try {
                val serviceId = com.example.BuildConfig.EMAILJS_SERVICE_ID
                val templateId = com.example.BuildConfig.EMAILJS_CUSTOMER_TEMPLATE_ID
                val userId = com.example.BuildConfig.EMAILJS_USER_ID
                
                if (serviceId.isBlank() || templateId.isBlank() || userId.isBlank()) {
                    return@launch
                }
                
                // For the customer template we use different parameter mappings:
                // We map "to_email" so it can be set in the EmailJS Template's "To Email" field
                val params = mapOf(
                    "to_email" to clientEmail, 
                    "to_name" to clientName,
                    "from_name" to "SiteNova Team",
                    "subject" to "Confirmation: $subject",
                    "message" to message
                )
                
                val request = com.example.data.api.EmailJsRequest(
                    serviceId = serviceId,
                    templateId = templateId,
                    userId = userId,
                    templateParams = params
                )
                
                com.example.data.api.EmailJsNetworkClient.emailJsApi.sendEmail(request)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

// Custom ViewModel Factory
class SiteNovaViewModelFactory(private val repository: SiteNovaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SiteNovaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SiteNovaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
