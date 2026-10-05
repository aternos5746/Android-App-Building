package com.example.permissiontest

import android.Manifest

/**
 * The one place to change which permission the app tests.
 * Also update the <uses-permission> line in AndroidManifest.xml.
 */
object PermissionConfig {
    const val PERMISSION = Manifest.permission.CAMERA
    const val DISPLAY_NAME = "Camera"
    const val EXPLANATION =
        "This app asks for camera access so it can demonstrate how Android permission " +
            "requests work. No photos or video are captured, stored, or sent anywhere. " +
            "If you deny, nothing breaks; you can grant it later from app settings."
}
