package io.deepmatter.pokemon.api.adapter

import io.deepmatter.pokemon.model.Rarity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RarityTypeAdapterTest {

    private lateinit var adapter: RarityTypeAdapter

    @Before
    fun setup() {
        adapter = RarityTypeAdapter()
    }

    @Test
    fun `correctly converts Common`() {
        assertThat(adapter.toJson(Rarity.Common)).isEqualTo(COMMON)
    }

    @Test
    fun `correctly converts Uncommon`() {
        assertThat(adapter.toJson(Rarity.Uncommon)).isEqualTo(UNCOMMON)
    }

    @Test
    fun `correctly converts Rare`() {
        assertThat(adapter.toJson(Rarity.Rare)).isEqualTo(RARE)
    }

    @Test
    fun `correctly converts Rare Holo`() {
        assertThat(adapter.toJson(Rarity.RareHolo)).isEqualTo(RARE_HOLO)
    }

    @Test
    fun `correctly converts Rare Ultra`() {
        assertThat(adapter.toJson(Rarity.RareUltra)).isEqualTo(RARE_ULTRA)
    }

    @Test
    fun `correctly converts Rare Secret`() {
        assertThat(adapter.toJson(Rarity.RareSecret)).isEqualTo(RARE_SECRET)
    }

    @Test
    fun `correctly parses Common`() {
        assertThat(adapter.fromJson("\"$COMMON\"")).isEqualTo(Rarity.Common)
    }

    @Test
    fun `correctly parses Uncommon`() {
        assertThat(adapter.fromJson("\"$UNCOMMON\"")).isEqualTo(Rarity.Uncommon)
    }

    @Test
    fun `correctly parses Rare`() {
        assertThat(adapter.fromJson("\"$RARE\"")).isEqualTo(Rarity.Rare)
    }

    @Test
    fun `correctly parses Rare Holo`() {
        assertThat(adapter.fromJson("\"$RARE_HOLO\"")).isEqualTo(Rarity.RareHolo)
    }

    @Test
    fun `correctly parses Rare Ultra`() {
        assertThat(adapter.fromJson("\"$RARE_ULTRA\"")).isEqualTo(Rarity.RareUltra)
    }

    @Test
    fun `correctly parses Rare Secret`() {
        assertThat(adapter.fromJson("\"$RARE_SECRET\"")).isEqualTo(Rarity.RareSecret)
    }

    @Test
    fun `correctly parses unrecognised values as Common`() {
        assertThat(adapter.fromJson("\"Rare Holo EX\"")).isEqualTo(Rarity.Common)
    }
}