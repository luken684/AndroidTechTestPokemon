package io.deepmatter.pokemon.core

import io.deepmatter.pokemon.model.Card
import io.deepmatter.pokemon.util.random.Randomiser
import io.deepmatter.pokemon.viewmodel.Round

class RoundFactoryImpl(private val randomiser: Randomiser) : RoundFactory {

    override fun buildRound(cards: List<Card>): Round = Round(cards.distinctBy { it.rarity }.take(2))
}