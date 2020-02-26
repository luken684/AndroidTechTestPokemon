package io.deepmatter.pokemon.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.deepmatter.pokemon.api.CardApi
import io.deepmatter.pokemon.core.RoundFactory
import io.deepmatter.pokemon.model.Card
import io.deepmatter.pokemon.util.event.Event
import io.deepmatter.pokemon.util.value.Value
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GameViewModelTest {

    private lateinit var model: GameViewModel

    @Mock
    private lateinit var api: CardApi

    @Mock
    private lateinit var factory: RoundFactory

    @Mock
    private lateinit var limit: Value<Int>

    @get: Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        model = GameViewModel(
            api,
            factory,
            limit,
            Schedulers.trampoline())
    }

    @Test
    fun `start begins loading cards`() {
        whenever(api.getCards())
            .thenReturn(Single.never())

        model.start()

        verify(api).getCards()
    }

    @Test
    fun `start shows loading state`() {
        whenever(api.getCards())
            .thenReturn(Single.never())

        model.start()

        assertThat(model.state.value).isEqualTo(State.Loading)
    }

    @Test
    fun `retry tapped begins loading cards`() {
        whenever(api.getCards())
            .thenReturn(Single.never())

        model.retryTapped()

        verify(api).getCards()
    }

    @Test
    fun `retry tapped loading state`() {
        whenever(api.getCards())
            .thenReturn(Single.never())

        model.retryTapped()

        assertThat(model.state.value).isEqualTo(State.Loading)
    }

    @Test
    fun `error loading cards shows error state`() {
        whenever(api.getCards())
            .thenReturn(Single.error(Exception()))

        model.start()

        assertThat(model.state.value).isEqualTo(State.Error)
    }

    @Test
    fun `success loading cards creates a round`() {
        val cards = listOf(Card(image = "A"))
        val round = Round(cards = listOf(Card(image = "B")))

        whenever(api.getCards())
            .thenReturn(Single.just(cards))

        whenever(factory.buildRound(any()))
            .thenReturn(round)

        model.start()

        verify(factory).buildRound(cards)
    }

    @Test
    fun `success loading cards shows round`() {
        val cards = listOf(Card(image = "A"))
        val round = Round(cards = listOf(Card(image = "B")))

        whenever(api.getCards())
            .thenReturn(Single.just(cards))

        whenever(factory.buildRound(any()))
            .thenReturn(round)

        model.start()

        assertThat(model.roundCards.value).isEqualTo(round.cards)
        assertThat(model.state.value).isEqualTo(State.Round)
    }

    @Test
    fun `card tapped indicates correct if correct card is tapped`() {
        val card = Card(image = "A")
        val round = Round(winner = card)

        whenever(api.getCards())
            .thenReturn(Single.just(emptyList()))

        whenever(factory.buildRound(any()))
            .thenReturn(round)

        whenever(limit.value)
            .thenReturn(5)

        model.start()
        model.cardTapped(card)

        assertThat(model.indicateCorrectSelection.value).isEqualTo(Event(Unit))
    }

    @Test
    fun `card tapped indicates incorrect if incorrect card is tapped`() {
        val card = Card(image = "A")
        val round = Round(winner = card)

        whenever(api.getCards())
            .thenReturn(Single.just(emptyList()))

        whenever(factory.buildRound(any()))
            .thenReturn(round)

        model.start()
        model.cardTapped(Card(image = "B"))

        assertThat(model.indicateIncorrectSelection.value).isEqualTo(Event(Unit))
    }

    @Test
    fun `card tapped creates a new round if incorrect card is tapped`() {
        val card = Card(image = "A")
        val round1 = Round(winner = card)
        val round2 = Round(cards = listOf(Card(image = "C")))

        whenever(api.getCards())
            .thenReturn(Single.just(emptyList()))

        whenever(factory.buildRound(any()))
            .thenReturn(round1, round2)

        model.start()
        model.cardTapped(Card(image = "B"))

        assertThat(model.roundCards.value).isEqualTo(round2.cards)
        assertThat(model.state.value).isEqualTo(State.Round)
    }

    @Test
    fun `card tapped creates a new round if correct card is tapped but score limit is not reached`() {
        val card = Card(image = "A")
        val round1 = Round(winner = card)
        val round2 = Round(cards = listOf(Card(image = "C")))

        whenever(api.getCards())
            .thenReturn(Single.just(emptyList()))

        whenever(factory.buildRound(any()))
            .thenReturn(round1, round2)

        whenever(limit.value)
            .thenReturn(5)

        model.start()
        model.cardTapped(card)

        assertThat(model.roundCards.value).isEqualTo(round2.cards)
        assertThat(model.state.value).isEqualTo(State.Round)
    }

    @Test
    fun `card tapped shows victory screen if correct card is tapped and score limit is reached`() {
        val card = Card(image = "A")
        val round = Round(winner = card)

        whenever(api.getCards())
            .thenReturn(Single.just(emptyList()))

        whenever(factory.buildRound(any()))
            .thenReturn(round)

        whenever(limit.value)
            .thenReturn(1)

        model.start()
        model.cardTapped(card)

        assertThat(model.state.value).isEqualTo(State.Victory)
    }
}