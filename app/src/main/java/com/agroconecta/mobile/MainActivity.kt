package com.agroconecta.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.agroconecta.mobile.ui.navigation.AppNavigation
import com.agroconecta.mobile.ui.theme.AgroConectaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgroConectaTheme {
                AppNavigation()
            }
        }
    }
}
