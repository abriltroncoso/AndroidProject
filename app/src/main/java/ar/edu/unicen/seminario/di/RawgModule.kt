package ar.edu.unicen.seminario.di

import ar.edu.unicen.seminario.ddl.data.RawgApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class RawgModule {

    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    fun providesApi(retrofit: Retrofit): RawgApi {
        return retrofit.create(RawgApi::class.java)
    }





}