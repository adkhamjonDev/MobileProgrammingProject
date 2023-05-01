package uz.adkhamjon.mobileprogrammingproject.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.adkhamjon.mobileprogrammingproject.data.remote.dto.Hit

interface ImageRepository {
    fun getImages(category: String): Flow<PagingData<Hit>>
}