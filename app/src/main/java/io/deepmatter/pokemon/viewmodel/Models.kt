package io.deepmatter.pokemon.viewmodel

import io.deepmatter.pokemon.model.Card

sealed class State {
    object Loading : State()
    object Error : State()
    object Round : State()
    object Victory : State()
}

data class Round(val cards: List<Card> = emptyList(),
                 val winner: Card = Card())