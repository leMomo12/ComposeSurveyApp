package com.mnowo.composesurveyapp.feature_statistics.presentation.statistic_screen.util

import java.math.RoundingMode
import java.text.DecimalFormat

object RoundOffDecimals {

    fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }
}