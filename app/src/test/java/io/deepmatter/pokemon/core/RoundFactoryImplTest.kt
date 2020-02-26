package io.deepmatter.pokemon.core

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.deepmatter.pokemon.model.Card
import io.deepmatter.pokemon.model.Rarity
import io.deepmatter.pokemon.util.random.Randomiser
import io.deepmatter.pokemon.viewmodel.Round
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RoundFactoryImplTest {

    private lateinit var factory: RoundFactory

    @Mock
    private lateinit var randomiser: Randomiser

    @Before
    fun setup() {
        factory = RoundFactoryImpl(
            randomiser)
    }
    @Test
    fun `generates a round with cards from two different rarities`() {
        val cards = listOf(
            common("A"),
            uncommon("B"),
            common("C"),
            common("D"))

        val expected = listOf(
            common("A"),
            uncommon("B"))

        whenever(randomiser.getIntInRange(any(), any()))
            .thenReturn(0)

        assertThat(factory.buildRound(cards).cards).isEqualTo(expected)
    }
    @Test
    fun `uncommon wins over common`() {
        val cards = listOf(
            common("A"),
            uncommon("B"))

        val expected = uncommon("B")

        whenever(randomiser.getIntInRange(any(), any()))
            .thenReturn(0)

        assertThat(factory.buildRound(cards).winner).isEqualTo(expected)
    }
    @Test
    fun `builds empty round if cards of only one rarity are supplied`() {
        val cards = listOf(
            common("A"),
            common("B"))

        val expected = Round()

        assertThat(factory.buildRound(cards)).isEqualTo(expected)
    }
    @Test
    fun `builds round with only 2 cards if multiple rarities are present`(){

        val cards = listOf(
            common("A"),
            uncommon("B"),
            rare("C"))

        val expected = 2

        assertThat(factory.buildRound(cards).cards.size).isEqualTo(expected)
    }
    @Test
    fun `rare beats uncommon`() {
        val cards = listOf(
            uncommon("A"),
            rare("B"))

        val expected = rare("B")

        whenever(randomiser.getIntInRange(any(), any()))
            .thenReturn(0)

        assertThat(factory.buildRound(cards).winner).isEqualTo(expected)
    }
    @Test
    fun `rareSecret beats common`() {
        val cards = listOf(
            common("A"),
            rareSecret("B"))

        val expected = rareSecret("B")

        whenever(randomiser.getIntInRange(any(), any()))
            .thenReturn(0)

        assertThat(factory.buildRound(cards).winner).isEqualTo(expected)

    }

    private fun common(image: String) = Card(
        image = image,
        rarity = Rarity.Common)

    private fun uncommon(image: String) = Card(
        image = image,
        rarity = Rarity.Uncommon)

    private fun rare(image: String) = Card(
        image = image,
        rarity = Rarity.Rare)

    private fun rareSecret(image: String) = Card(
        image = image,
        rarity = Rarity.RareSecret)
}