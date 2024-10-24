package com.cb.meapps.presentation.ui.common

import android.widget.NumberPicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.cb.meapps.presentation.ui.common.preview.SupportedDevicesPreview

@Composable
fun NumberPicker(
    value: Int,
    range: IntRange,
    onChanged: (newValue: Int) -> Unit
) {
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

@Composable
fun ValuePicker(
    value: Int,
    values: List<String>,
    onChanged: (newValue: Int) -> Unit
) {
    AndroidView(
        factory = { context ->
            NumberPicker(context).apply {
                minValue = 0
                maxValue = values.size - 1
                displayedValues = values.toTypedArray()
                setValue(value)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        update = {
            it.setOnValueChangedListener { _, _, newVal ->
                onChanged(newVal)
            }
        }
    )
}

@SupportedDevicesPreview
@Composable
private fun PreviewPickers() {
    Surface {
        Column {
            NumberPicker(value = 0, range = IntRange(0, 10)) {}
            ValuePicker(value = 0, values = listOf("Code", "Music", "Travel")) {}
        }
    }
}