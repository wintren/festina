package app.sample.studio.theme.typo

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import app.sample.studio.R

/**
 * The fonts used (size, weight etc.) are stolen, but the system this uses is created by me.
 * Faster to use some existing fonts rather then try to play designer.
 */
internal object ChronoTypo {

    // Adding Some font from Resource for show
    private val familyDmSans = FontFamily(
        Font(resId = R.font.dm_sans_regular, weight = FontWeight.Normal),
        Font(R.font.dm_sans_italic, style = FontStyle.Italic),
        Font(R.font.dm_sans_medium, weight = FontWeight.Medium),
        Font(R.font.dm_sans_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
        Font(R.font.dm_sans_bold, weight = FontWeight.Bold),
        Font(R.font.dm_sans_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    )

    private val baseTextStyle = TextStyle(fontFamily = familyDmSans)

    private fun style(fontSize: TextUnit, lineHeight: TextUnit, fontWeight: FontWeight, letterSpacing: TextUnit) =
        baseTextStyle.copy(
            fontSize = fontSize,
            lineHeight = lineHeight,
            fontWeight = fontWeight,
            letterSpacing = letterSpacing,
        )

    val headline1Medium by lazy { style(30.sp, 32.4.sp, W500, 0.3.sp) }
    val headline1Bold by lazy { style(30.sp, 32.4.sp, W700, 0.3.sp) }
    val headline2Regular by lazy { style(24.sp, 27.84.sp, W400, (-0.48).sp) }
    val headline2Bold by lazy { style(24.sp, 27.84.sp, W700, (-0.48).sp) }
    val headline3Regular by lazy { style(18.sp, 20.34.sp, W400, (-0.48).sp) }
    val headline3Bold by lazy { style(18.sp, 20.34.sp, W700, (-0.48).sp) }
    val headline4 by lazy { style(16.sp, 18.08.sp, W700, (-0.48).sp) }
    val body1Regular by lazy { style(16.sp, 19.2.sp, W400, (-0.16).sp) }
    val body1Medium by lazy { style(16.sp, 19.2.sp, W500, (-0.16).sp) }
    val body2Regular by lazy { style(14.sp, 16.8.sp, W400, 0.sp) }
    val label1 by lazy { style(20.sp, 22.6.sp, W700, (-0.16).sp) }
    val label2Medium by lazy { style(16.sp, 18.08.sp, W500, 0.sp) }
    val label2Bold by lazy { style(16.sp, 18.08.sp, W700, (-0.32).sp) }
    val label3 by lazy { style(14.sp, 15.82.sp, W500, 0.sp) }
    val label3Medium by lazy { style(14.sp, 15.82.sp, W500, 0.sp) }
    val label3Bold by lazy { style(14.sp, 15.82.sp, W700, 0.sp) }
    val label4Medium by lazy { style(12.sp, 14.4.sp, W500, 0.sp) }
    val label4Bold by lazy { style(12.sp, 14.4.sp, W700, 0.sp) }

    fun m3(): Typography = Typography(
        displayLarge = headline1Bold,
        displayMedium = headline1Medium,
        displaySmall = headline2Regular,
        headlineLarge = headline2Bold,
        headlineMedium = headline3Bold,
        headlineSmall = headline3Regular,
        titleLarge = label1,
        titleMedium = headline4,
        titleSmall = label2Bold,
        bodyLarge = body1Medium,
        bodyMedium = body1Regular,
        bodySmall = body2Regular,
        labelLarge = label3,
        labelMedium = label4Bold,
        labelSmall = label4Medium
    )

}
