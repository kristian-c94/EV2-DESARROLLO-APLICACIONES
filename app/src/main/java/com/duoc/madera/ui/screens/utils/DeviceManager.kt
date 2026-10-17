package com.duoc.madera.ui.screens.utils

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun obtWindowSizeClass(): WindowSizeClass {
    return calculateWindowSizeClass(LocalContext.current as android.app.Activity)
}