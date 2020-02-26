package io.deepmatter.pokemon.model

import com.google.gson.annotations.SerializedName

data class CardWrapper(@SerializedName("cards") val cards: List<Card> = emptyList())

data class Card(@SerializedName("imageUrl") val image: String = "",
                @SerializedName("rarity") val rarity: Rarity = Rarity.Common)

sealed class Rarity {
    object Common : Rarity()
    object Uncommon : Rarity()
    object Rare : Rarity()
    object RareHolo : Rarity()
    object RareUltra : Rarity()
    object RareSecret : Rarity()
}
