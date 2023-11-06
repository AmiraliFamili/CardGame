
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

    private int playerNum;
    protected LinkedList<LinkedList<Integer>> decks = new LinkedList<LinkedList<Integer>>();
    protected LinkedList<LinkedList<Integer>> players = new LinkedList<LinkedList<Integer>>();
    private LinkedList<Integer> pack;

    public CardGame(int playerNumber) {
        this.playerNum = playerNumber;
        createPack(playerNumber);// all these actions should always happen in order
        setPlayers();
        setDecks();
        this.players = dealHands(pack);
        this.decks = dealDecks(pack);
    }

    public void emptyPack() {
        this.pack = new LinkedList<Integer>();
    }

    public LinkedList<Integer> getPack() {
        return this.pack;
    }

    public LinkedList<Integer> createPack(int n) {
        if (this.pack == null) {
            emptyPack();
        }
        Random random = new Random();
        for (int i = 1; i <= (n * 2); i++) {
            for (int j = 1; j <= 4; j++)
                pack.add(i);
        }
        Collections.shuffle(pack);
        return pack;
    }

    public int get1FromPack() {
        int card = this.pack.poll();
        return card;
    }

    public void setPlayers() {
        for (int i = 0; i < playerNum; i++) {
            this.players.add(new LinkedList<Integer>());
        }
    }

    public void setDecks() {
        for (int i = 0; i < playerNum; i++) {
            this.decks.add(new LinkedList<Integer>());
        }
    }

    public LinkedList<LinkedList<Integer>> dealHands(LinkedList<Integer> pack) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < playerNum; j++) {
                LinkedList<Integer> playerN = this.players.get(j);
                int card = get1FromPack();
                playerN.add(card);
            }
        }

        return this.players;
    }

    public LinkedList<LinkedList<Integer>> dealDecks(LinkedList<Integer> pack) {

        int index = 0;
        for (Integer card : pack) {
            LinkedList<Integer> deck = this.decks.get(index);
            deck.add(card);
            index = (index + 1) % playerNum;
        }
        return decks;
    }

    public LinkedList<LinkedList<Integer>> getHands() {
        return this.players;
    }

    public LinkedList<LinkedList<Integer>> getDecks() {
        return this.decks;
    }

    public static void main(String[] args) {
        InputOutput obj = new InputOutput();
        CardGame cardGame = new CardGame(obj.getInput());
        Card card = new Card(cardGame.getDecks(), cardGame.getHands());
        card.startGame();// main

        cardGame.getPack();

    }
}