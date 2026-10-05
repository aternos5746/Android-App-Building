package com.example.permissiontest

import org.junit.Assert.assertEquals
import org.junit.Test

class PermissionStateTest {
    @Test
    fun granted() =
        assertEquals(PermissionState.Granted, resolvePermissionState(true, false, true))

    @Test
    fun freshInstall() =
        assertEquals(PermissionState.NotRequested, resolvePermissionState(false, false, false))

    @Test
    fun deniedOnce() =
        assertEquals(PermissionState.Denied, resolvePermissionState(false, true, true))

    @Test
    fun permanentlyDenied() =
        assertEquals(PermissionState.PermanentlyDenied, resolvePermissionState(false, false, true))
}
