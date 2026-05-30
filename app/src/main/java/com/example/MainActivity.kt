package com.example

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.data.db.AppDatabase
import com.example.data.repository.SiteNovaRepository
import com.example.ui.navigation.ROUTE_AUTH
import com.example.ui.navigation.ROUTE_HOME
import com.example.ui.navigation.SiteNovaApp
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.SiteNovaViewModel
import com.example.ui.viewmodel.SiteNovaViewModelFactory
import com.example.worker.InactivityWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    
    // Lazy instantations matching Clean Architecture with Simple Constructor Injection DI
    private val database by lazy { AppDatabase.getDatabase(this) }
    private val repository by lazy { SiteNovaRepository(database.quoteDao(), database.bookedCallDao()) }
    
    // ViewModels injection using model factory
    private val viewModel: SiteNovaViewModel by viewModels {
        SiteNovaViewModelFactory(repository)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        // We just record permission result, nothing else specifically required
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // EDGE-TO-EDGE ENABLE
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        scheduleInactivityWorker()

        val sharedPrefs = getSharedPreferences("sitenova_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPrefs.getBoolean("logged_in", false)
        val startRoute = if (isLoggedIn) ROUTE_HOME else ROUTE_AUTH
        
        setContent {
            MyApplicationTheme {
                SiteNovaApp(
                    viewModel = viewModel,
                    startRoute = startRoute,
                    onAuthSuccess = {
                        sharedPrefs.edit().putBoolean("logged_in", true).apply()
                    }
                )
            }
        }
    }

    private fun scheduleInactivityWorker() {
        val workRequest = OneTimeWorkRequestBuilder<InactivityWorker>()
            .setInitialDelay(3, TimeUnit.DAYS)
            .build()
        WorkManager.getInstance(this).enqueueUniqueWork(
            "inactivity_work",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }
}
