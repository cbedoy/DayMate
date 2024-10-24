package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.R
import com.cb.meapps.domain.fake.getFakeDocs
import com.cb.meapps.domain.model.Document
import com.cb.meapps.domain.model.FileType
import com.cb.meapps.presentation.ui.common.BodyMedium
import com.cb.meapps.presentation.ui.common.DayMateScaffold
import com.cb.meapps.presentation.ui.common.preview.SupportedDevicesPreview
import com.cb.meapps.presentation.viewmodel.DocsState
import kotlinx.coroutines.launch

// Inspired on https://dribbble.com/shots/14933297/attachments/6649396?mode=media
@Composable
fun DocsScreen(
    state: DocsState
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    DayMateScaffold(
        title = stringResource(R.string.docs_title),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("No implemented")
                    }
                },
                content = {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            )
        },
        snackbarHostState = snackbarHostState
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier.padding(paddingValues = paddingValues),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.documents) {
                DocumentView(it)
            }
        }
    }
}

@Composable
private fun DocumentView(document: Document) {
    Column(
        Modifier
            .fillMaxSize()
            .border(1.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(8.dp))
            .padding(12.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        DocumentIcon(document.fileType)
        Spacer(modifier = Modifier.height(16.dp))
        BodyMedium(
            text = document.fileName
        )
    }
}

@Composable
private fun DocumentIcon(fileType: FileType) {
    val resId = when(fileType) {
        FileType.PDF -> R.drawable.baseline_picture_as_pdf_24
        FileType.IMAGE -> R.drawable.baseline_broken_image_24
        FileType.VIDEO -> R.drawable.baseline_music_video_24
        FileType.AUDIO -> R.drawable.baseline_audiotrack_24
        FileType.OTHER -> R.drawable.baseline_attach_file_24
    }
    Icon(
        painter = painterResource(id = resId),
        contentDescription = fileType.name,
        modifier = Modifier.size(
            80.dp, 40.dp
        ),
        tint = MaterialTheme.colorScheme.primary
    )
}

@SupportedDevicesPreview
@Composable
fun PreviewBureaucraticDocsScreen() {
    Surface {
        DocsScreen(
            state = DocsState(
                documents = getFakeDocs()
            )
        )
    }
}