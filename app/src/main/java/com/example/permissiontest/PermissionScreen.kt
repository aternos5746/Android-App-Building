package com.example.permissiontest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PermissionScreen(
    state: PermissionState,
    log: List<String>,
    onRequest: () -> Unit,
    onOpenSettings: () -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                "${PermissionConfig.DISPLAY_NAME} permission",
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(PermissionConfig.EXPLANATION, style = MaterialTheme.typography.bodyMedium)

            Surface(color = statusColor(state), shape = MaterialTheme.shapes.small) {
                Text(
                    "Status: ${state.label}",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                )
            }

            when (state) {
                PermissionState.Granted ->
                    Button(onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth()) {
                        Text("Permission granted")
                    }
                PermissionState.PermanentlyDenied ->
                    Button(onClick = onOpenSettings, modifier = Modifier.fillMaxWidth()) {
                        Text("Open app settings")
                    }
                else ->
                    Button(onClick = onRequest, modifier = Modifier.fillMaxWidth()) {
                        Text("Request permission")
                    }
            }

            Text("Event log", style = MaterialTheme.typography.titleSmall)
            if (log.isEmpty()) Text("No events yet.", style = MaterialTheme.typography.bodySmall)
            log.forEach { Text(it, style = MaterialTheme.typography.bodySmall) }
        }
    }
}

private fun statusColor(state: PermissionState): Color = when (state) {
    PermissionState.Granted -> Color(0xFFC8E6C9)
    PermissionState.Denied -> Color(0xFFFFE0B2)
    PermissionState.PermanentlyDenied -> Color(0xFFFFCDD2)
    PermissionState.NotRequested -> Color(0xFFE0E0E0)
}
