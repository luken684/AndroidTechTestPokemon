package io.deepmatter.pokemon.api

import io.deepmatter.pokemon.model.Card
import io.reactivex.Single

interface CardApi {

    fun getCards(): Single<List<Card>>
}