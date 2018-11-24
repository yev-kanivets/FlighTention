package com.m360.android.core.utils

import android.content.Context
import android.content.res.Resources

/**
 * Created by renaud on 23/11/15.
 */
object DpPxUtils {

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixel
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(dp: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        return dp * (metrics.densityDpi / 160f)
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Float, context: Context): Float {
        val metrics = Resources.getSystem().displayMetrics
        return px / (metrics.densityDpi / 160f)
    }

}
