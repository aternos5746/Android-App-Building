package com.example.permissiontest

enum class PermissionState(val label: String) {
    NotRequested("Not requested yet"),
    Granted("Granted"),
    Denied("Denied (can ask again)"),
    PermanentlyDenied("Denied permanently (use Settings)"),
}

/**
 * Pure resolver with no Android dependencies, so it is unit-testable.
 *
 * Android has no direct "permanently denied" API. After a denial, rationale == false
 * combined with a recorded earlier request means the system will no longer show the dialog.
 */
fun resolvePermissionState(
    granted: Boolean,
    shouldShowRationale: Boolean,
    requestedBefore: Boolean,
): PermissionState = when {
    granted -> PermissionState.Granted
    shouldShowRationale -> PermissionState.Denied
    requestedBefore -> PermissionState.PermanentlyDenied
    else -> PermissionState.NotRequested
}
