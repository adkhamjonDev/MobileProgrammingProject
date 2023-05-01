package uz.adkhamjon.mobileprogrammingproject.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.adkhamjon.mobileprogrammingproject.data.remote.ImageApiService
import uz.adkhamjon.mobileprogrammingproject.data.repository.ImageRepositoryImpl
import uz.adkhamjon.mobileprogrammingproject.domain.repository.ImageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DIModule {

    @Singleton
    @Provides
    fun provideGsonConvertorFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonGsonConverterFactory: GsonConverterFactory,
        builder: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pixabay.com/api/")
            .client(builder)
            .addConverterFactory(gsonGsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideImageApiService(retrofit: Retrofit): ImageApiService {
        return retrofit.create(ImageApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideImageRepository(
        imageApiService: ImageApiService
    ): ImageRepository {
        return ImageRepositoryImpl(imageApiService)
    }
}