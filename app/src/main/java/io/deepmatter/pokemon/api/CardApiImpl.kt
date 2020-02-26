package io.deepmatter.pokemon.api

import io.deepmatter.pokemon.model.Card
import io.reactivex.Scheduler
import io.reactivex.Single

class CardApiImpl(private val api: RetrofitCardApi,
                  private val io: Scheduler,
                  private val computation: Scheduler) : CardApi {

    override fun getCards(): Single<List<Card>> = api
        .getCards()
        .subscribeOn(io)
        .observeOn(computation)
        .map { it.cards }
}