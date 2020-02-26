package io.deepmatter.pokemon.core
import io.deepmatter.pokemon.api.adapter.COMMON
import io.deepmatter.pokemon.model.Card
import io.deepmatter.pokemon.model.Rarity
import io.deepmatter.pokemon.util.random.Randomiser
import io.deepmatter.pokemon.viewmodel.Round

class RoundFactoryImpl(private val randomiser: Randomiser) : RoundFactory {

    override fun buildRound(cards: List<Card>): Round {
        val cards1 = cards.distinctBy { it.rarity }.take(2)
        val rareCard = cards1.find { it.rarity == Rarity.Rare }
        val uncommonCard=cards1.find { it.rarity == Rarity.Uncommon }
        val commonCard = cards1.find { it.rarity == Rarity.Common }

        if(rareCard == null){
            return Round(cards1, uncommonCard!!)
        }
        else  if (uncommonCard ==null) {
        return Round(cards1, commonCard!!)
    }
        else {return Round(cards1, rareCard)
}
    }
}