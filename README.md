# MultiThreaded CardGame

- this is a MultiThreaded Card Game at the start of the game the program asks for a number and a location from terminal which represent the number of players (or Threads) and the location of a valid pack to be load to the system, respectively.
- A valid pack is a text file which contains one Integer in each line and it should contain at least (6 x players) to be able to run the game, the program keeps asking for a valid player number and a valid pack.
- after reading the input the CardGame class will distribute the cards between players and decks in a round robin fashion (just like a poker game one card at a time to each player) and passes the players and decks distributed to their relevant Classes, after that the main game method is called via the main method in CardGame class and the game starts. 
- players and decks form a ring topology and draw a card from their own deck and discard a card to the next player's deck, this playing style will continue until a player wins the game.
- All the actions made by players are written in a text file with the corresponding name for the player in players folder and the final decks recorded by the program is available in the decks folder.
- When a player collects 4 cards with the same suit (e.g. [5,5,5,5]), they immediately win the game. 
- relevant test files are available to test the robust ness of the project
- this is a coursework for ECM2414 , Software Development , Computer Science
- further information is available in Report.pdf file.

    # File Guide

    ## There are Total of 5 Files and 3 Directories for this Card Game : 

    ## cards.jar : is the main jar file used to run this project

    ### Inside Jar : 

    1. CardGame.java : is the main class in which the main game is implemented, when the constructor of this class is called it takes an Integer and a LinkedList representing the number of players and the pack respectively. It would then distribute the cards among players, fill the decks with remaining cards in the pack, passes the players and decks lists to their relevant Classes (Card.java, Player.java), it would also call the InputOutput class to get it to write players initial hand to their corresponding files.

    2. InputOutput.java : is one of the core classes of this project, it's responsible for getting and validating user's inputs (player number and pack file), it's also responsible for creating, deleting and writing the correct status of the game in the player and deck files including the initial hand of players, their current hand, and final hand as well as the winner (the winner will inform other players that they have won the game and the game finished) and the final deck content, all these writes are made within the only two directories used for this project, players directory contains all the files for specific players and their game plan, decks directory contains all the available decks, in each deck file there is a single line indicating the final elements of each game at the end.

    3. Card.java : Card class manages and contains all the relevant live information about decks, it's constructor will receive a decks list from the CardGame Class and communicates to the main game method with that list, it's also responsible for taking and inserting cards to the player's left and right deck.

    4. Player.java : Player class manages and contains all the relevant live information regarding players, similar to Card class, it also receives the initial hands of players and communicates with the game with it, it's also responsible for detecting which card should be discarded from player's hand and replace it with the obtained card from their left deck. 

    5. pack.txt : a pack used for testing the functionality of the game and for demonstrating what a valid pack looks like.

    6. Report.pdf : contains a full scaled report regarding the source code and test suites, mostly about the design used for development of these classes and the known problems to the developer as well as a detailed description of how these classes are operating together.

    ### Outside Jar : 

    1. players : is a directory that is used for creating, deleting and modifying player files using InputOutput class.

    2. decks : is a directory that is used for creating, deleting and modifying deck files using InputOutput class.

    3. cardsTest.zip : is a directory which contains all the test suites sufficient for unit testing the entire project, to check, validate and test the functionality, availability, correctness and robustness of the entire project, further information is available inside the README.md file inside the test directory.

    4. CardGamePrintOutCode.pdf : is the printout of all the source files including test classes



# Developer

- Name : Amirali
- Last Name : Famili
- Student Number : 720060845
- Candidate Number : 206898
- Module : ECM2414
- Module Leader : Majeed Soufian
- Academic Year : 2023-2024