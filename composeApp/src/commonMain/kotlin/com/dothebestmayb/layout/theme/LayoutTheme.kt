package com.dothebestmayb.layout.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.dothebestmayb.Layout.core.designsystem.theme.DarkColorScheme
import com.dothebestmayb.Layout.core.designsystem.theme.DarkExtendedColors
import com.dothebestmayb.Layout.core.designsystem.theme.LightColorScheme
import com.dothebestmayb.Layout.core.designsystem.theme.LightExtendedColors
import com.dothebestmayb.Layout.core.designsystem.theme.LocalExtendedColors

@Composable
fun LayoutTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extendedScheme = if (darkTheme) DarkExtendedColors else LightExtendedColors

    // extended Color를 Composable 어디서나 접근할 수 있도록 LocalComposition에 설정
    CompositionLocalProvider(LocalExtendedColors provides extendedScheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }

}