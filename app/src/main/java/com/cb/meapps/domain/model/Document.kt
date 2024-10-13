package com.cb.meapps.domain.model

data class Document(
    val fileName: String,
    val alias: String,
    val filePath: String,
    val fileType: FileType
)

enum class FileType {
    PDF, IMAGE, VIDEO, AUDIO, OTHER
}