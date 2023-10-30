package pack;

import java.util.*;
/*
 * should we use GUI
 * number of player 
 * can we use sleep and wait for the threads to form the round robin fashion (time slices)
 * how should we approach the cards of the game ? 
 * how can we define multiple variables for players and decks to work together 
 *      or should we use nested lists approach for both players and decks
 * 
 * The card they discard must display a value which is not of their preferred denomination.
Additionally, a player must not hold onto a non-preferred denomination card indefinitely, so you
must implement your Player class to reflect this restriction (otherwise the game may stagnate).
D
 * 
 */
public class CardGame {

    public int getInput() {
        Scanner scan = new Scanner(System.in);
        String playerNum = scan.nextLine();
        return 0;
    }

    public static void main(String[] args) {
        CardGame obj = new CardGame();
        Card card = new Card(4);

        System.out.println(card.createPack(4));


        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                
            }
        });

    }
}