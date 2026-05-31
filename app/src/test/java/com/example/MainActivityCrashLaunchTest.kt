package com.example

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ActivityScenario
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.junit.Assert

@RunWith(AndroidJUnit4::class)
@Config(sdk = [34])
class MainActivityCrashLaunchTest {
    @Test
    fun testActivityLaunches() {
        var launched = false
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                launched = true
            }
        }
        Assert.assertTrue(launched)
    }
}
