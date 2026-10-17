package com.duoc.madera.ui.screens

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.duoc.madera.ui.theme.EnciclopediaMaderasTheme


class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnciclopediaMaderasTheme(){
                HomeScreen2();
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun  GreetingPreview(){
    EnciclopediaMaderasTheme (){
        HomeScreen2()
    }
}