package io.deepmatter.pokemon.api.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import io.deepmatter.pokemon.model.Rarity

const val COMMON = "Common"
const val UNCOMMON = "Uncommon"
const val RARE = "Rare"
const val RARE_HOLO = "Rare Holo"
const val RARE_ULTRA = "Rare Ultra"
const val RARE_SECRET = "Rare Secret"

class RarityTypeAdapter : TypeAdapter<Rarity>() {

    override fun write(writer: JsonWriter, value: Rarity) {
        when(value) {
            Rarity.Uncommon -> UNCOMMON
            Rarity.Rare -> RARE
            Rarity.RareHolo -> RARE_HOLO
            Rarity.RareUltra -> RARE_ULTRA
            Rarity.RareSecret-> RARE_SECRET
            else -> COMMON
        }.let(writer::jsonValue)
    }

    override fun read(reader: JsonReader): Rarity = reader
        .takeIf { it.hasNext() }
        ?.takeIf { it.peek() == JsonToken.STRING }
        ?.nextString()
        ?.let(::rarity)
        ?: Rarity.Common

    private fun rarity(rarity: String) = when(rarity) {
        UNCOMMON -> Rarity.Uncommon
        RARE -> Rarity.Rare
        RARE_HOLO -> Rarity.RareHolo
        RARE_ULTRA -> Rarity.RareUltra
        RARE_SECRET -> Rarity.RareSecret
        else -> Rarity.Common
    }
}