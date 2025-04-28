package com.saikrishna.reachmobi.presentation.models

import android.media.Image
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector
)
