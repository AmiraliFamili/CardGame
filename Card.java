import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Card {

    private int playerNum;
    protected LinkedList<LinkedList<Integer>> decks = new LinkedList<LinkedList<Integer>>();
    protected LinkedList<LinkedList<Integer>> players = new LinkedList<LinkedList<Integer>>();
    private LinkedList<Integer> pack;

    public Card(int playerNumber) {
        this.playerNum = playerNumber;
        createPack(playerNumber);// all these actions should always happen in order
        // here we should save the file
        // since the program removes the cards when dealHands and dealDecks methods are called 
        //InputOutput in = new InputOutput(); // pack is full here 

        setPlayers();
        setDecks();
        this.players = dealHands(pack);
        this.decks = dealDecks(pack);

        //InputOutput inn = new InputOutput();// pack is empty here 

        //InputOutput in = new InputOutput();
    }

    

    public Card() {
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
        while (!this.pack.isEmpty()) {
            LinkedList<Integer> deck = this.decks.get(index);
            deck.add(get1FromPack());
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

}