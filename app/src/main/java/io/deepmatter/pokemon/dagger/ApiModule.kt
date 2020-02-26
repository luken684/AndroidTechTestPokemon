package io.deepmatter.pokemon.dagger

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.deepmatter.pokemon.R
import io.deepmatter.pokemon.api.CardApi
import io.deepmatter.pokemon.api.CardApiImpl
import io.deepmatter.pokemon.api.RetrofitCardApi
import io.deepmatter.pokemon.api.adapter.RarityTypeAdapter
import io.deepmatter.pokemon.model.Rarity
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @Provides
    fun provideCardApi(api: RetrofitCardApi): CardApi =
        CardApiImpl(
            api,
            Schedulers.io(),
            Schedulers.computation())

    @Provides
    fun provideCardRetrofitApi(okhttp: OkHttpClient,
                               retrofit: Retrofit.Builder): RetrofitCardApi =
        retrofit
            .client(okhttp)
            .build()
            .create(RetrofitCardApi::class.java)

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()

    @Provides
    fun provideRetrofitBuilder(gson: Gson,
                               context: Context) =
        Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(context.getString(R.string.server_url))

    @Provides
    fun provideGson(): Gson =
        GsonBuilder()
            .registerTypeAdapter(Rarity::class.java, RarityTypeAdapter())
            .create()
}