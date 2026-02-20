package com.dothebestmayb.layout.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import layout.composeapp.generated.resources.Res
import layout.composeapp.generated.resources.pretendard_bold
import layout.composeapp.generated.resources.pretendard_light
import layout.composeapp.generated.resources.pretendard_medium
import layout.composeapp.generated.resources.pretendard_regular
import layout.composeapp.generated.resources.pretendard_semibold

val PretendardFontFamily
    @Composable get() = FontFamily(
        Font(
            resource = Res.font.pretendard_light,
            weight = FontWeight.Light,
        ),
        Font(
            resource = Res.font.pretendard_regular,
            weight = FontWeight.Normal,
        ),
        Font(
            resource = Res.font.pretendard_medium,
            weight = FontWeight.Medium,
        ),
        Font(
            resource = Res.font.pretendard_semibold,
            weight = FontWeight.SemiBold,
        ),
        Font(
            resource = Res.font.pretendard_bold,
            weight = FontWeight.Bold,
        ),
    )

val Typography.labelXSmall: TextStyle
    @Composable get() = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 14.sp,
    )

val Typography.titleXSmall: TextStyle
    @Composable get() = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp,
    )

val Typography
    @Composable get() = Typography(
        titleLarge = TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = (-0.5).sp
        ),
        titleMedium = TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = 30.sp,
            letterSpacing = (-0.2).sp
        ),
        titleSmall = TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        bodySmall = TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        labelLarge = TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 24.sp
        ),
        labelMedium = TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        labelSmall = TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 18.sp
        ),
        displaySmall = TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.sp,
            lineHeight = 14.sp
        ),
    )