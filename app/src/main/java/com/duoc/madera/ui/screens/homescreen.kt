package com.duoc.madera.ui.screens
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.duoc.madera.ui.screens.utils.obtWindowSizeClass


@Composable
fun HomeScreen2 (){
    val windowSizeClass= obtWindowSizeClass()
    when (windowSizeClass.widthSizeClass){
        WindowWidthSizeClass.Compact -> HomeScreenS()
        WindowWidthSizeClass.Medium ->HomeScreenMedium()
        WindowWidthSizeClass.Expanded-> HomeScreenL()
    }
}