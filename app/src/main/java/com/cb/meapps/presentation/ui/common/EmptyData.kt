package com.cb.meapps.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.R
import com.cb.meapps.presentation.ui.common.preview.SupportedDevicesPreview

@Composable
fun EmptyData(title: String, subtitle: String, modifier: Modifier) {
    Column(
        modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        Alignment.CenterHorizontally
    ){
        Image(painter = painterResource(id = R.drawable.common_empty_data), contentDescription = null)
        BodyMediumBold(text = title)
        BodyMediumLightPrimary(text = subtitle)
    }
}

@SupportedDevicesPreview
@Composable
private fun PreviewEmptyData() {
    Surface {
        EmptyData("Ops!", "No tracks to be shown", Modifier)
    }
}