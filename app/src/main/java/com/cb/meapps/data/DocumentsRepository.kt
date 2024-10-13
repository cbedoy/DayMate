package com.cb.meapps.data

import com.cb.meapps.data.dao.DocumentDao
import com.cb.meapps.data.model.DocumentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DocumentsRepository @Inject constructor(
    private val documentDao: DocumentDao
) {
    fun getAllDocuments(): Flow<List<DocumentEntity>> = documentDao.getAllDocuments()

    suspend fun saveDocument(document: DocumentEntity) {
        documentDao.insertDocument(document)
    }

    suspend fun deleteDocument(document: DocumentEntity) {
        documentDao.deleteDocument(document)
    }
}
