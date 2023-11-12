# MultiThreaded CardGame

- this is a multithreaded, thread-safe cardgame developed in java programming language, 
    the game works by entering the number of players and a file location for the report part of the game.

- After entering both, the game will start by creating a pack (of size player number times 8) and distributing the cards in that pack in a round robin fashion between firstly players which are recognized as indexes of a nested Linkedlist and then similarly the decks until there are no cards left in the pack. (this should result in each player having 4 cards and a deck which also consists of 4 cards)

- then each player is assigned by a thread and takes a card from their deck and give a card that they don't need to the player deck who is going to play after them . (Note that players are not real and they are just some processors working together)

- the game is finished when a player collects 4 cards with the same suit (e.g. [5,5,5,5])

- relevant test files are available to test the robust ness of the project

- this is a coursework for ECM2414 , Software Development , Computer Science

- further information is available in Report.pdf file.