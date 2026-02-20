package com.dothebestmayb.Layout.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.dothebestmayb.layout.theme.LayoutBase0
import com.dothebestmayb.layout.theme.LayoutBase100
import com.dothebestmayb.layout.theme.LayoutBase1000
import com.dothebestmayb.layout.theme.LayoutBase1000Alpha14
import com.dothebestmayb.layout.theme.LayoutBase1000Alpha80
import com.dothebestmayb.layout.theme.LayoutBase100Alpha10Alt
import com.dothebestmayb.layout.theme.LayoutBase150
import com.dothebestmayb.layout.theme.LayoutBase200
import com.dothebestmayb.layout.theme.LayoutBase400
import com.dothebestmayb.layout.theme.LayoutBase500
import com.dothebestmayb.layout.theme.LayoutBase700
import com.dothebestmayb.layout.theme.LayoutBase800
import com.dothebestmayb.layout.theme.LayoutBase900
import com.dothebestmayb.layout.theme.LayoutBase950
import com.dothebestmayb.layout.theme.LayoutBlue
import com.dothebestmayb.layout.theme.LayoutBrand100
import com.dothebestmayb.layout.theme.LayoutBrand1000
import com.dothebestmayb.layout.theme.LayoutBrand500
import com.dothebestmayb.layout.theme.LayoutBrand500Alpha40
import com.dothebestmayb.layout.theme.LayoutBrand600
import com.dothebestmayb.layout.theme.LayoutBrand900
import com.dothebestmayb.layout.theme.LayoutCakeDarkBlue
import com.dothebestmayb.layout.theme.LayoutCakeDarkGreen
import com.dothebestmayb.layout.theme.LayoutCakeDarkMint
import com.dothebestmayb.layout.theme.LayoutCakeDarkOrange
import com.dothebestmayb.layout.theme.LayoutCakeDarkPink
import com.dothebestmayb.layout.theme.LayoutCakeDarkPurple
import com.dothebestmayb.layout.theme.LayoutCakeDarkRed
import com.dothebestmayb.layout.theme.LayoutCakeDarkTeal
import com.dothebestmayb.layout.theme.LayoutCakeDarkViolet
import com.dothebestmayb.layout.theme.LayoutCakeDarkYellow
import com.dothebestmayb.layout.theme.LayoutCakeLightBlue
import com.dothebestmayb.layout.theme.LayoutCakeLightGreen
import com.dothebestmayb.layout.theme.LayoutCakeLightMint
import com.dothebestmayb.layout.theme.LayoutCakeLightOrange
import com.dothebestmayb.layout.theme.LayoutCakeLightPink
import com.dothebestmayb.layout.theme.LayoutCakeLightPurple
import com.dothebestmayb.layout.theme.LayoutCakeLightRed
import com.dothebestmayb.layout.theme.LayoutCakeLightTeal
import com.dothebestmayb.layout.theme.LayoutCakeLightViolet
import com.dothebestmayb.layout.theme.LayoutCakeLightYellow
import com.dothebestmayb.layout.theme.LayoutGreen
import com.dothebestmayb.layout.theme.LayoutGrey
import com.dothebestmayb.layout.theme.LayoutLightBlue
import com.dothebestmayb.layout.theme.LayoutOrange
import com.dothebestmayb.layout.theme.LayoutPink
import com.dothebestmayb.layout.theme.LayoutPurple
import com.dothebestmayb.layout.theme.LayoutRed200
import com.dothebestmayb.layout.theme.LayoutRed500
import com.dothebestmayb.layout.theme.LayoutRed600
import com.dothebestmayb.layout.theme.LayoutTeal
import com.dothebestmayb.layout.theme.LayoutViolet
import com.dothebestmayb.layout.theme.LayoutYellow
import com.dothebestmayb.layout.theme.LayoutBackground

val LocalExtendedColors = staticCompositionLocalOf {
    LightExtendedColors
}

val ColorScheme.extended: ExtendedColors
    @ReadOnlyComposable
    @Composable
    get() = LocalExtendedColors.current

@Immutable
data class ExtendedColors(
    // Button states
    val primaryHover: Color,
    val destructiveHover: Color,
    val destructiveSecondaryOutline: Color,
    val disabledOutline: Color,
    val disabledFill: Color,
    val successOutline: Color,
    val success: Color,
    val onSuccess: Color,
    val secondaryFill: Color,

    // Text variants
    val textPrimary: Color,
    val textTertiary: Color,
    val textSecondary: Color,
    val textPlaceholder: Color,
    val textDisabled: Color,

    // Surface variants
    val surfaceLower: Color,
    val surfaceHigher: Color,
    val surfaceOutline: Color,
    val overlay: Color,

    // Accent colors
    val accentBlue: Color,
    val accentPurple: Color,
    val accentViolet: Color,
    val accentPink: Color,
    val accentOrange: Color,
    val accentYellow: Color,
    val accentGreen: Color,
    val accentTeal: Color,
    val accentLightBlue: Color,
    val accentGrey: Color,

    // Cake colors for chat bubbles
    val cakeViolet: Color,
    val cakeGreen: Color,
    val cakeBlue: Color,
    val cakePink: Color,
    val cakeOrange: Color,
    val cakeYellow: Color,
    val cakeTeal: Color,
    val cakePurple: Color,
    val cakeRed: Color,
    val cakeMint: Color,

    // 좌석도 배경색
    val layoutBackground: Color,
)

val LightExtendedColors = ExtendedColors(
    primaryHover = LayoutBrand600,
    destructiveHover = LayoutRed600,
    destructiveSecondaryOutline = LayoutRed200,
    disabledOutline = LayoutBase200,
    disabledFill = LayoutBase150,
    successOutline = LayoutBrand100,
    success = LayoutBrand600,
    onSuccess = LayoutBase0,
    secondaryFill = LayoutBase100,

    textPrimary = LayoutBase1000,
    textTertiary = LayoutBase800,
    textSecondary = LayoutBase900,
    textPlaceholder = LayoutBase700,
    textDisabled = LayoutBase400,

    surfaceLower = LayoutBase100,
    surfaceHigher = LayoutBase100,
    surfaceOutline = LayoutBase1000Alpha14,
    overlay = LayoutBase1000Alpha80,

    accentBlue = LayoutBlue,
    accentPurple = LayoutPurple,
    accentViolet = LayoutViolet,
    accentPink = LayoutPink,
    accentOrange = LayoutOrange,
    accentYellow = LayoutYellow,
    accentGreen = LayoutGreen,
    accentTeal = LayoutTeal,
    accentLightBlue = LayoutLightBlue,
    accentGrey = LayoutGrey,

    cakeViolet = LayoutCakeLightViolet,
    cakeGreen = LayoutCakeLightGreen,
    cakeBlue = LayoutCakeLightBlue,
    cakePink = LayoutCakeLightPink,
    cakeOrange = LayoutCakeLightOrange,
    cakeYellow = LayoutCakeLightYellow,
    cakeTeal = LayoutCakeLightTeal,
    cakePurple = LayoutCakeLightPurple,
    cakeRed = LayoutCakeLightRed,
    cakeMint = LayoutCakeLightMint,

    layoutBackground = LayoutBackground,
)

val DarkExtendedColors = ExtendedColors(
    primaryHover = LayoutBrand600,
    destructiveHover = LayoutRed600,
    destructiveSecondaryOutline = LayoutRed200,
    disabledOutline = LayoutBase900,
    disabledFill = LayoutBase1000,
    successOutline = LayoutBrand500Alpha40,
    success = LayoutBrand500,
    onSuccess = LayoutBase1000,
    secondaryFill = LayoutBase900,

    textPrimary = LayoutBase0,
    textTertiary = LayoutBase200,
    textSecondary = LayoutBase150,
    textPlaceholder = LayoutBase400,
    textDisabled = LayoutBase500,

    surfaceLower = LayoutBase1000,
    surfaceHigher = LayoutBase900,
    surfaceOutline = LayoutBase100Alpha10Alt,
    overlay = LayoutBase1000Alpha80,

    accentBlue = LayoutBlue,
    accentPurple = LayoutPurple,
    accentViolet = LayoutViolet,
    accentPink = LayoutPink,
    accentOrange = LayoutOrange,
    accentYellow = LayoutYellow,
    accentGreen = LayoutGreen,
    accentTeal = LayoutTeal,
    accentLightBlue = LayoutLightBlue,
    accentGrey = LayoutGrey,

    cakeViolet = LayoutCakeDarkViolet,
    cakeGreen = LayoutCakeDarkGreen,
    cakeBlue = LayoutCakeDarkBlue,
    cakePink = LayoutCakeDarkPink,
    cakeOrange = LayoutCakeDarkOrange,
    cakeYellow = LayoutCakeDarkYellow,
    cakeTeal = LayoutCakeDarkTeal,
    cakePurple = LayoutCakeDarkPurple,
    cakeRed = LayoutCakeDarkRed,
    cakeMint = LayoutCakeDarkMint,

    layoutBackground = LayoutBackground,
)

val LightColorScheme = lightColorScheme(
    primary = LayoutBrand500,
    onPrimary = LayoutBase0, // 버튼 텍스트는 흰색으로 가독성 확보
    primaryContainer = LayoutBrand100,
    onPrimaryContainer = LayoutBrand1000,

    secondary = LayoutBase700,
    onSecondary = LayoutBase0,
    secondaryContainer = LayoutBase150,
    onSecondaryContainer = LayoutBase900,

    tertiary = LayoutBrand900,
    onTertiary = LayoutBase0,
    tertiaryContainer = LayoutBrand100,
    onTertiaryContainer = LayoutBrand1000,

    error = LayoutRed500,
    onError = LayoutBase0,
    errorContainer = LayoutRed200,
    onErrorContainer = LayoutRed600,

    background = LayoutBase0, // 배경은 완전히 깨끗한 화이트
    onBackground = LayoutBase1000,
    surface = LayoutBase0,
    onSurface = LayoutBase1000,
    surfaceVariant = LayoutBase100,
    onSurfaceVariant = LayoutBase800,

    outline = LayoutBase200, // 심플한 외곽선
    outlineVariant = LayoutBase150,
)

val DarkColorScheme = darkColorScheme(
    primary = LayoutBrand500,
    onPrimary = LayoutBase0,
    primaryContainer = LayoutBrand900,
    onPrimaryContainer = LayoutBrand100,

    secondary = LayoutBase400,
    onSecondary = LayoutBase1000,
    secondaryContainer = LayoutBase900,
    onSecondaryContainer = LayoutBase200,

    tertiary = LayoutBrand500,
    onTertiary = LayoutBase0,
    tertiaryContainer = LayoutBrand900,
    onTertiaryContainer = LayoutBrand100,

    error = LayoutRed500,
    onError = LayoutBase0,
    errorContainer = LayoutRed600,
    onErrorContainer = LayoutRed200,

    background = LayoutBase1000, // 깊이 있는 다크 네이비
    onBackground = LayoutBase100,
    surface = LayoutBase950, // 레이어 구분을 위한 약간 밝은 다크
    onSurface = LayoutBase0,
    surfaceVariant = LayoutBase900,
    onSurfaceVariant = LayoutBase200,

    outline = LayoutBase800,
    outlineVariant = LayoutBase700,
)
