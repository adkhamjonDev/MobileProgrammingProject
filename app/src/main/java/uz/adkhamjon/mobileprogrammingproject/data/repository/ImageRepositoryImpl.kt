package uz.adkhamjon.mobileprogrammingproject.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.adkhamjon.mobileprogrammingproject.data.paging.ImagePagination
import uz.adkhamjon.mobileprogrammingproject.data.remote.ImageApiService
import uz.adkhamjon.mobileprogrammingproject.data.remote.dto.Hit
import uz.adkhamjon.mobileprogrammingproject.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageApiService: ImageApiService
) : ImageRepository {
    override fun getImages(category: String): Flow<PagingData<Hit>> =
        Pager(PagingConfig(100)) {
            ImagePagination(imageApiService, category)
        }.flow

}