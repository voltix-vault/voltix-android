package com.vultisig.wallet.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ColorsPalette(
    val transparent: Color = Color.Unspecified,
    val neutral0: Color = Color.Unspecified,
    val neutral100: Color = Color.Unspecified,
    val neutral200: Color = Color.Unspecified,
    val neutral300: Color = Color.Unspecified,
    val neutral500: Color = Color.Unspecified,
    val neutral600: Color = Color.Unspecified,
    val neutral700: Color = Color.Unspecified,
    val neutral800: Color = Color.Unspecified,
    val neutral900: Color = Color.Unspecified,
//-------------------------------------------------------
    val ultramarine: Color = Color.Unspecified,
    val mediumPurple: Color = Color.Unspecified,
    val Error: Color = Color.Unspecified,
    val darkPurpleMain: Color = Color.Unspecified,
    val darkPurple800: Color = Color.Unspecified,
    val darkPurple500: Color = Color.Unspecified,
//-------------------------------------------------------
    val oxfordBlue800: Color = Color(0xFF02122B),
    val oxfordBlue600Main: Color = Color.Unspecified,
    val oxfordBlue400: Color = Color.Unspecified,
    val oxfordBlue200: Color = Color.Unspecified,
    val persianBlue800: Color = Color.Unspecified,
    val persianBlue600Main: Color = Color.Unspecified,
    val persianBlue400: Color = Color.Unspecified,
    val persianBlue200: Color = Color.Unspecified,
    val turquoise800: Color = Color.Unspecified,
    val turquoise600Main: Color = Color.Unspecified,
    val turquoise400: Color = Color.Unspecified,
    val turquoise200: Color = Color.Unspecified,
)

val OnLightCustomColorsPalette = ColorsPalette(
    transparent = Color(0x00000000),
    neutral0 = Color(0xffFFFFFF),
    neutral100 = Color(0xffF3F4F5),
    neutral200 = Color(0xffEBECED),
    neutral300 = Color(0xffBDBDBD),
    neutral500 = Color(0xff9F9F9F),
    neutral600 = Color(0xff777777),
    neutral700 = Color(0xff3E3E3E),
    neutral800 = Color(0xff101010),
    neutral900 = Color(0xff000000),

    ultramarine = Color(0xff390F94),
    mediumPurple = Color(0xff9563FF),
    Error = Color(0xffFFB400),
    darkPurpleMain = Color(0xff0F0623),
    darkPurple800 = Color(0xff1E103E),
    darkPurple500 = Color(0xff3B2D59),

    oxfordBlue800 = Color(0xff02122B),
    oxfordBlue600Main = Color(0xff061B3A),
    oxfordBlue400 = Color(0xff11284A),
    oxfordBlue200 = Color(0xff1B3F73),
    persianBlue800 = Color(0xff042D9A),
    persianBlue600Main = Color(0xff0439C7),
    persianBlue400 = Color(0xff2155DF),
    persianBlue200 = Color(0xff4879FD),
    turquoise800 = Color(0xff15D7AC),
    turquoise600Main = Color(0xff33E6BF),
    turquoise400 = Color(0xff81F8DE),
    turquoise200 = Color(0xffA6FBE8),
)

val OnDarkCustomColorsPalette = ColorsPalette(
    transparent = Color(0x00000000),
    neutral0 = Color(0xffFFFFFF),
    neutral100 = Color(0xffF3F4F5),
    neutral200 = Color(0xffEBECED),
    neutral300 = Color(0xffBDBDBD),
    neutral500 = Color(0xff9F9F9F),
    neutral600 = Color(0xff777777),
    neutral700 = Color(0xff3E3E3E),
    neutral800 = Color(0xff101010),
    neutral900 = Color(0xff000000),

    ultramarine = Color(0xff390F94),
    mediumPurple = Color(0xff9563FF),
    Error = Color(0xffFFB400),
    darkPurpleMain = Color(0xff0F0623),
    darkPurple800 = Color(0xff1E103E),
    darkPurple500 = Color(0xff3B2D59),

    oxfordBlue800 = Color(0xff02122B),
    oxfordBlue600Main = Color(0xff061B3A),
    oxfordBlue400 = Color(0xff11284A),
    oxfordBlue200 = Color(0xff1B3F73),
    persianBlue800 = Color(0xff042D9A),
    persianBlue600Main = Color(0xff0439C7),
    persianBlue400 = Color(0xff2155DF),
    persianBlue200 = Color(0xff4879FD),
    turquoise800 = Color(0xff15D7AC),
    turquoise600Main = Color(0xff33E6BF),
    turquoise400 = Color(0xff81F8DE),
    turquoise200 = Color(0xffA6FBE8),
)
