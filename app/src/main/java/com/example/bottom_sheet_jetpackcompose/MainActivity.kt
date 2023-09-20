package com.example.bottom_sheet_jetpackcompose

import BottomSheetContent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.bottom_sheet_jetpackcompose.ui.theme.BottomsheetjetpackcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var darkTheme by remember{ mutableStateOf((false)) }

            BottomsheetjetpackcomposeTheme(darkTheme=darkTheme,) {
                // Assuming you have appropriate values for selectedCategory and onCategorySelected
                val selectedCategory: String = "Todo List" // Replace this with the actual selected category
                val onCategorySelected: (String) -> Unit = { category -> /* Your implementation for handling the selected category */ }

                BottomSheetContent(selectedCategory, onCategorySelected,)
            }
        }
    }
}



