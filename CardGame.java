
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

        try {
            int num = Integer.parseInt(playerNum); 
            return num;
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number as number of players : \n");
            return getInput(); 
        }
    }


    public static void main(String[] args) {
        CardGame obj = new CardGame();
        Card card = new Card(obj.getInput());

        System.out.println(card.getPack());
        System.out.println(card.getPack());
        card.setPlayers();
        System.out.println(card.getPlayers());
        System.out.println(card.dealHands(card.getPack()));
        System.out.println(card.getPack());
        card.setDecks();
        System.out.println(card.dealDecks(card.getPack()));


        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                
            }
        });

    }
}