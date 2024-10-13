package com.cb.meapps.domain.mapper

import com.cb.meapps.data.model.DocumentEntity
import com.cb.meapps.domain.model.Document
import com.cb.meapps.domain.model.FileType
import javax.inject.Inject

class DocsMapper @Inject constructor() {
    fun transform(documents: List<DocumentEntity>) : List<Document> {
        return documents.map {
            Document(
                fileName = it.fileName,
                alias = it.alias,
                filePath = it.fileType,
                fileType = FileType.valueOf(it.fileType)
            )
        }
    }
}