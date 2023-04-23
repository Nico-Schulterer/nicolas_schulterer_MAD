package com.example.learningdiary.models

import androidx.compose.ui.graphics.vector.ImageVector

data class DropDownItem(
    val name: String,
    val icon: ImageVector,
    val route: String
)