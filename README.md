Android Tech Challenge (Pokemon)
------

This codebase includes a near complete implementation of a card game application. The application is complete with the exception of the RoundFactoryImpl class which is a stub. You must implement the RoundFactoryImpl so that it allows the game to be played.

Tests are included for the class in RoundFactoryImplTest; however, they are not exhaustive. You are required to add at least three tests and provide comments in the code to say why you feel the test is required.

_Note: The applications is written in Kotlin, you may change the RoundFactoryImpl and RoundFactoryImplTest to Java if you prefer._

##### Application Requirements

The requirements for the finished application are as follows:

1. The app must show a loading screen while loading the application’s data.
2. The app must load a list of Pokémon cards from the above api.
3. The game must present a user with just the image for two cards and ask them which is rarer.
4. A user must never be given two cards with the same rarity to pick from.
5. Card rarity is defined as follows from least rare to most rare:
  * common
  * uncommon
  * rare
  * rare holo
  * rare ultra
  * rare secret
6. Tapping the correct card will award the player 1 point.
7. Tapping the incorrect card will have no effect on the user’s score.
8. A user will be informed if they have selected correctly.
9. After tapping a card the user will be presented with two new cards.
10. When a user gains 5 points the user will be shown a success screen.

You are required to complete RoundFactoryImpl to implement points 2 and 3 above, and you are required to provide the above tests.

##### Submission

Place your submission in a GitHub private repo and add _deepmatter-recruitment_ as a collaborator. We recommend that you fork this repo to create your private repo.
