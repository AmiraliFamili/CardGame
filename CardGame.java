
import java.util.*;

/*
 * should we use GUI
 * number of player 
 * can we use sleep and wait for the threads to form the round robin fashion (time slices)
 * how should we approach the cards of the game ? 
 * how can we define multiple variables for players and decks to work together 
 *      or should we use nested lists approach for both players and decks
 * The card they discard must display a value which is not of their preferred denomination.
Additionally, a player must not hold onto a non-preferred denomination card indefinitely, so you
must implement your Player class to reflect this restriction (otherwise the game may stagnate).
D
 * 
 */
public class CardGame {
    public static void main(String[] args) {
        //InputOutput obj = new InputOutput();
        Card cardGame = new Card(10);

        Player player = new Player(cardGame.getDecks(), cardGame.getHands());
        player.startGame();// main


    System.out.println("Hand : " +cardGame.getHands());
    System.out.println(cardGame.getDecks());
        System.out.println(cardGame.getPack());

    }
}



/*
 * The provided code is a Java implementation of a card game. Here's a breakdown of what the code does:

Class and Variable Declarations: The class CardGame is declared with several instance variables. playerNum is an integer representing the number of players in the game. decks and players are LinkedLists of LinkedLists of Integers, representing the decks and hands of cards for each player. pack is a LinkedList of Integers representing the card pack from which the decks and hands are drawn.
Constructor: The constructor CardGame(int playerNumber) initializes the playerNum variable and calls several methods to create the card pack, set up the players and decks, and deal the hands and decks.
Card Pack Creation: The createPack(int n) method creates a card pack with n * 2 cards, where each card number is repeated 4 times (once for each suit). The cards are then shuffled.
Player and Deck Setup: The setPlayers() method initializes a new LinkedList for each player, and the setDecks() method does the same for the decks.
Card Dealing: The dealHands(LinkedList<Integer> pack) method deals 4 cards to each player from the card pack. The dealDecks(LinkedList<Integer> pack) method distributes the remaining cards in the pack to the decks in a round-robin fashion.
Getters: The getHands() and getDecks() methods return the hands and decks of each player, respectively.
Main Method: The main(String[] args) method creates an instance of CardGame with a specified number of players, then creates a Card object to start the game.
This code uses a nested LinkedList approach to represent the players and their hands, and the decks of cards. This is a common approach when you need to associate multiple values (in this case, the cards) with a single key (the player or the deck) geeksforgeeks.org, programiz.com, geeksforgeeks.org.

The LinkedList class in Java provides several useful methods for manipulating lists, such as add(), remove(), get(), poll(), and peek(). These methods are used throughout the code to manage the players, decks, and card pack docs.oracle.com, programiz.com.

The Random class is used to shuffle the cards in the pack, ensuring that the cards are dealt in a random order. The poll() method is used to remove and return the first card from the pack, which is then added to a player's hand or a deck docs.oracle.com.

The game logic, such as the rules for discarding cards and the conditions for winning the game, are not implemented in this code. They would need to be added in a separate Card class or similar.
 */