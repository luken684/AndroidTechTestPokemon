package io.deepmatter.pokemon.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.deepmatter.pokemon.api.CardApi
import io.deepmatter.pokemon.core.RoundFactory
import io.deepmatter.pokemon.extensions.perform
import io.deepmatter.pokemon.model.Card
import io.deepmatter.pokemon.util.event.Event
import io.deepmatter.pokemon.util.value.Value
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

class GameViewModel(private val api: CardApi,
                    private val factory: RoundFactory,
                    private val scoreLimit: Value<Int>,
                    private val main: Scheduler) : ViewModel() {

    val state = MutableLiveData<State>()
    val roundCards = MutableLiveData<List<Card>>()

    val indicateCorrectSelection = MutableLiveData<Event<Unit>>()
    val indicateIncorrectSelection = MutableLiveData<Event<Unit>>()

    private lateinit var cards: List<Card>

    private var score = 0
    private var round: Round = Round()
        set(value) {
            field = value
            roundCards.value = value.cards
        }

    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()
    }

    fun start() = load()

    fun retryTapped() = load()

    fun cardTapped(card: Card) =
        if(round.winner == card) correctCardTapped()
        else incorrectCardTapped()

    private fun load() {
        state.value = State.Loading
        disposable?.dispose()
        disposable = api
            .getCards()
            .observeOn(main)
            .subscribe(::success, ::error)
    }

    private fun success(cards: List<Card>) {
        this.cards = cards
        beginRound()
    }

    private fun error(throwable: Throwable) {
        state.value = State.Error
    }

    private fun beginRound() {
        round = factory.buildRound(cards)
        state.value = State.Round
    }

    private fun correctCardTapped() {
        indicateCorrectSelection.perform()
        score++
        if (score >= scoreLimit.value) state.value = State.Victory
        else beginRound()
    }

    private fun incorrectCardTapped() {
        indicateIncorrectSelection.perform()
        beginRound()
    }
}