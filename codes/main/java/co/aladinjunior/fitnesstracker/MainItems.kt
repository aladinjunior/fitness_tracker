package co.aladinjunior.fitnesstracker

import android.graphics.drawable.AdaptiveIconDrawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MainItems(
    val id: Int,
    @StringRes val strinTxtId: Int,
    @DrawableRes val drawableId: Int,
    val color: Int,
)



