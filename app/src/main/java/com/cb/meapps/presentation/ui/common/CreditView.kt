package com.cb.meapps.presentation.ui.common

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun CreditView() {
    val context = LocalContext.current

    Column(
        Modifier.clickable {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/carlos-cervantes-bedoy-34248187/"))
            context.startActivity(intent)
        }
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Black.copy(alpha = 0.2f)))
        Text(
            text = "Made with ❤\uFE0F\uD83C\uDF2E by Carlos Bedoy",
            Modifier
                .background(MaterialTheme.colorScheme.onPrimary)
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}