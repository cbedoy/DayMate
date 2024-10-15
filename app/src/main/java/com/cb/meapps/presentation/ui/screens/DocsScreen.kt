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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.meapps.R
import com.cb.meapps.domain.model.Document
import com.cb.meapps.domain.model.FileType
import com.cb.meapps.presentation.ui.common.CommonTopAppBar
import com.cb.meapps.presentation.viewmodel.DocsState

// Inspired on https://dribbble.com/shots/14933297/attachments/6649396?mode=media
@Composable
fun DocsScreen(
    state: DocsState
) {
    Scaffold(
        topBar = {
            CommonTopAppBar(title = stringResource(R.string.docs_title))
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  },
                content = {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
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
        Text(
            text = document.fileName,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary
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

@Preview
@Composable
fun PreviewBureaucraticDocsScreen() {
    Surface {
        DocsScreen(
            state = DocsState(
                documents = listOf(
                    Document(
                        fileName = "file1.pdf",
                        alias = "Document PDF 1",
                        filePath = "/storage/emulated/0/Documents/file1.pdf",
                        fileType = FileType.PDF
                    ),
                    Document(
                        fileName = "image1.png",
                        alias = "Profile Image",
                        filePath = "/storage/emulated/0/Pictures/image1.png",
                        fileType = FileType.IMAGE
                    ),
                    Document(
                        fileName = "video1.mp4",
                        alias = "Vacation Video",
                        filePath = "/storage/emulated/0/Movies/video1.mp4",
                        fileType = FileType.VIDEO
                    ),
                    Document(
                        fileName = "audio1.mp3",
                        alias = "Favorite Song",
                        filePath = "/storage/emulated/0/Music/audio1.mp3",
                        fileType = FileType.AUDIO
                    ),
                    Document(
                        fileName = "file2.docx",
                        alias = "Work Document",
                        filePath = "/storage/emulated/0/Documents/file2.docx",
                        fileType = FileType.OTHER
                    ),
                    Document(
                        fileName = "presentation.pptx",
                        alias = "Project Presentation",
                        filePath = "/storage/emulated/0/Documents/presentation.pptx",
                        fileType = FileType.OTHER
                    ),
                    Document(
                        fileName = "file3.pdf",
                        alias = "User Manual",
                        filePath = "/storage/emulated/0/Documents/file3.pdf",
                        fileType = FileType.PDF
                    ),
                    Document(
                        fileName = "image2.jpg",
                        alias = "Landscape Image",
                        filePath = "/storage/emulated/0/Pictures/image2.jpg",
                        fileType = FileType.IMAGE
                    )
                )
            )
        )
    }
}