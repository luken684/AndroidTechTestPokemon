package io.deepmatter.pokemon.api

import io.deepmatter.pokemon.model.CardWrapper
import io.reactivex.Single
import retrofit2.http.GET

interface RetrofitCardApi {

    @GET("v1/cards")
    fun getCards(): Single<CardWrapper>
}