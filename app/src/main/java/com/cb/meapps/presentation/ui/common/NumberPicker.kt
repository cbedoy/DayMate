package com.cb.meapps.presentation.ui.common

import android.widget.NumberPicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun NumberPicker(value: Int, range: IntRange, onChanged: (newValue: Int) -> Unit) {
    AndroidView(factory = { context ->
        NumberPicker(context).apply {
            minValue =  range.first
            maxValue = range.last
            setValue(value)
        }
    }, update = {
        it.setOnValueChangedListener { _, _, newVal ->
            onChanged(newVal)
        }
    })
}
