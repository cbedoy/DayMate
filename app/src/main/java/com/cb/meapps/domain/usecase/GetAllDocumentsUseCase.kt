package com.cb.meapps.domain.usecase

import com.cb.meapps.data.DocumentsRepository
import com.cb.meapps.domain.fake.IsFakeDataEnabledUseCase
import com.cb.meapps.domain.fake.getFakeDocs
import com.cb.meapps.domain.mapper.DocsMapper
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllDocumentsUseCase @Inject constructor(
    private val isFakeDataEnabledUseCase: IsFakeDataEnabledUseCase,
    private val documentsRepository: DocumentsRepository,
    private val docsMapper: DocsMapper
) {
    operator fun invoke() = flow {
        documentsRepository.getAllDocuments().collect { documents ->
            if (isFakeDataEnabledUseCase()) {
                emit(getFakeDocs())
            } else {
                emit(docsMapper.transform(documents))
            }
        }
    }
}