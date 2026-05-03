package com.agroconecta.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import com.agroconecta.mobile.ui.navigation.AppNavigation
import com.agroconecta.mobile.ui.theme.AgroConectaTheme

@AndroidEntryPoint
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