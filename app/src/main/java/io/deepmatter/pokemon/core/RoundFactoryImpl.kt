package io.deepmatter.pokemon.core
import io.deepmatter.pokemon.api.CardApi
import io.deepmatter.pokemon.model.Card
import io.deepmatter.pokemon.model.Rarity
import io.deepmatter.pokemon.util.random.Randomiser
import io.deepmatter.pokemon.viewmodel.Round

class RoundFactoryImpl(private val randomiser: Randomiser) : RoundFactory {

    override fun buildRound(cards: List<Card>): Round {
        val cardsUsed = cards.toMutableList().shuffled()
        val cards1 = cardsUsed.distinctBy { it.rarity }.take(2)
        val rareSecretCard = cards1.find { it.rarity == Rarity.RareSecret }
        val rareUltraCard = cards1.find { it.rarity == Rarity.RareUltra}
        val rareHoloCard = cards1.find { it.rarity == Rarity.RareHolo}
        val rareCard = cards1.find { it.rarity == Rarity.Rare }
        val uncommonCard=cards1.find { it.rarity == Rarity.Uncommon }
        val commonCard = cards1.find { it.rarity == Rarity.Common }

        return if(rareSecretCard !=null){
            Round(cards1, rareSecretCard)
        }
          else if(rareUltraCard !=null){
            Round(cards1, winner = rareUltraCard)
        } else if(rareHoloCard != null){
            Round(cards1, rareHoloCard)
        } else if(rareCard != null){
            Round(cards1, rareCard)
        } else  if (uncommonCard !=null) {
            Round(cards1, uncommonCard)
        } else {
            Round(cards1, commonCard!!)
        }
    }
}