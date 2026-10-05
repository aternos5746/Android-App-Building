package com.example.permissiontest

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val prefs by lazy { getSharedPreferences("permission_test", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                var state by remember { mutableStateOf(currentState()) }
                val log = remember { mutableStateListOf<String>() }

                fun record(event: String) {
                    val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                    log.add(0, "$time  $event")
                }

                val launcher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { granted ->
                    prefs.edit().putBoolean(KEY_REQUESTED, true).apply()
                    state = currentState()
                    record("Dialog result: granted=$granted -> ${state.name}")
                }

                // Picks up changes made in system Settings while the app was backgrounded.
                LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
                    val previous = state
                    state = currentState()
                    if (state != previous) record("Resume: ${previous.name} -> ${state.name}")
                }

                PermissionScreen(
                    state = state,
                    log = log,
                    onRequest = {
                        record("Requesting ${PermissionConfig.PERMISSION}")
                        launcher.launch(PermissionConfig.PERMISSION)
                    },
                    onOpenSettings = {
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", packageName, null),
                            )
                        )
                    },
                )
            }
        }
    }

    private fun currentState(): PermissionState = resolvePermissionState(
        granted = ContextCompat.checkSelfPermission(this, PermissionConfig.PERMISSION) ==
            PackageManager.PERMISSION_GRANTED,
        shouldShowRationale = shouldShowRequestPermissionRationale(PermissionConfig.PERMISSION),
        requestedBefore = prefs.getBoolean(KEY_REQUESTED, false),
    )

    private companion object {
        const val KEY_REQUESTED = "requested_once"
    }
}
