package com.readychatai.dev.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.readychatai.dev.presentation.model.MessageCategory
import com.readychatai.dev.ui.theme.unSelectedTabColor

@Composable
fun CategoryChip(
    category: MessageCategory,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) Color.Black else unSelectedTabColor,
        tonalElevation = if (isSelected) 0.dp else 2.dp
    ) {
        Text(
            text = category.label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            color = if (isSelected) {
                Color.White
            } else {
                Color.Black
            },
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}