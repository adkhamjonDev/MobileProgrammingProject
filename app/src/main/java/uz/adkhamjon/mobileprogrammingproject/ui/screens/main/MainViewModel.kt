package uz.adkhamjon.mobileprogrammingproject.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import uz.adkhamjon.mobileprogrammingproject.data.remote.dto.Hit
import uz.adkhamjon.mobileprogrammingproject.domain.repository.ImageRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    fun getImages(category: String): Flow<PagingData<Hit>> {
        return imageRepository.getImages(category).cachedIn(viewModelScope)
    }

}