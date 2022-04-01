package com.chatty.compose.ui.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Vibrator
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.unit.dp
import com.chatty.compose.ui.theme.ok

fun Context.hideIME() {
    (getSystemService(ComponentActivity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow((this as Activity).currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}
fun Context.vibrate(time: Long) {
    (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
        .vibrate(time)
}
fun Context.hasTorch(): Boolean {
    return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
}
fun Modifier.drawLoginStateRing() = this.then(
    object : DrawModifier {
        override fun ContentDrawScope.draw() {
            val circleRadius = 20.dp.toPx()
            drawContent()
            drawCircle(
                color = Color.White,
                radius = circleRadius,
                center = Offset(drawContext.size.width - circleRadius , drawContext.size.height - circleRadius)
            )
            drawCircle(
                color = ok,
                radius = circleRadius * 4 / 5,
                center = Offset(drawContext.size.width - circleRadius, drawContext.size.height - circleRadius)
            )
        }
    }
)
