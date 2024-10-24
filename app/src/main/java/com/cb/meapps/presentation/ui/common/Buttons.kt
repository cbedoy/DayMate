package com.cb.meapps.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.R
import com.cb.meapps.presentation.ui.common.preview.SupportedDevicesPreview


@Composable
fun SmallPrimaryButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick,
        Modifier,
        enabled = enabled
    ) {
        Text(text)
    }
}

@Composable
fun PrimaryButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick,
        Modifier.fillMaxWidth(),
        enabled = enabled
    ) {
        Text(text)
    }
}

@Composable
fun SecondaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick,
        modifier.fillMaxWidth(),
        enabled = enabled
    ) {
        Text(text)
    }
}

@Composable
fun SmallSecondaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick,
        modifier,
        enabled = enabled
    ) {
        Text(text)
    }
}

@Composable
fun TextButton(text: String, icon: Int = R.drawable.baseline_settings_24, enabled: Boolean = true, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick, enabled = enabled),
        Arrangement.spacedBy(16.dp),
        Alignment.CenterVertically
    ) {
        if (icon != 0) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        BodyMediumSemiBold(text = text, modifier = Modifier.padding(vertical = 16.dp))
    }
}

@SupportedDevicesPreview
@Composable
private fun PreviewButtons() {
    Surface {
        Column {
            SmallPrimaryButton(text = "Small Primary") {}
            SmallPrimaryButton(text = "Small Primary disabled", false) {}
            PrimaryButton(text = "Primary") {}
            PrimaryButton(text = "Primary disabled", false) {}
            SmallSecondaryButton(text = "Small Secondary") {}
            SmallSecondaryButton(text = "Small Secondary disabled", enabled = false) {}
            SecondaryButton(text = "Secondary") {}
            SecondaryButton(text = "Secondary", enabled = false) {}
            TextButton(text = "Text action") {}
            TextButton(text = "Text action", 0) {}
        }
    }
}
