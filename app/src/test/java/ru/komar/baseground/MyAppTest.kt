package ru.komar.baseground

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ru.komar.disk.DiskCounterModel
import ru.komar.featureowner.testInstallation
import ru.komar.navigation_menu.CounterModel
import ru.komar.navigation_menu.CounterModelProvider

@Config(
    sdk = [ Build.VERSION_CODES.P ]
)
@RunWith(RobolectricTestRunner::class)
class MyAppTest {

    /**
     * Test cases:
     * 1. Missing dependency implementation
     * 2. Undeclared dependency access
     * 3. Duplicate dependency implementation (resolution failure)
     * 4. Installation success (root component initialization, etc.)
     */
    @Test
    fun testPluginsInstalledCorrectly() {
        ApplicationProvider
            .getApplicationContext<MyApp>()
            .testInstallation()
    }

}
