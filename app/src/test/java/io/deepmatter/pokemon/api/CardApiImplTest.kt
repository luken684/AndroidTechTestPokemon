package io.deepmatter.pokemon.api

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.deepmatter.pokemon.model.Card
import io.deepmatter.pokemon.model.CardWrapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardApiImplTest {

    private lateinit var api: CardApiImpl

    @Mock
    private lateinit var retrofit: RetrofitCardApi

    @Before
    fun setup() {
        api = CardApiImpl(
            retrofit,
            Schedulers.trampoline(),
            Schedulers.trampoline())
    }

    @Test
    fun `get cards call retrofit`() {
        whenever(retrofit.getCards())
            .thenReturn(Single.never())

        api
            .getCards()
            .test()

        verify(retrofit).getCards()
    }

    @Test
    fun `get cards propagates error from retrofit`() {
        val error = Exception()

        whenever(retrofit.getCards())
            .thenReturn(Single.error(error))

        api
            .getCards()
            .test()
            .assertError(error)
    }

    @Test
    fun `get cards returns cards from api`() {
        val cards = listOf(Card(image = "A"))

        whenever(retrofit.getCards())
            .thenReturn(Single.just(CardWrapper(cards = cards)))

        api
            .getCards()
            .test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(cards)
    }
}