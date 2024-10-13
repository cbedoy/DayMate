package com.cb.meapps.domain.usecase

import com.cb.meapps.data.DocumentsRepository
import com.cb.meapps.domain.mapper.DocsMapper
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllDocumentsUseCase @Inject constructor(
    private val documentsRepository: DocumentsRepository,
    private val docsMapper: DocsMapper
) {
    operator fun invoke() = flow {
        documentsRepository.getAllDocuments().collect { documents ->
            emit(docsMapper.transform(documents))
        }
    }
}