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

- 

# Developer

Name : Amirali
Last Name : Famili
Student Number : 720060845
Candidate Number : 206898
Module : ECM2414
Module Leader : Majeed Soufian
Academic Year : 2023-2024