import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Card {

    protected int playerNum;
    protected LinkedList<LinkedList<Integer>> decks = new LinkedList<LinkedList<Integer>>();
    protected LinkedList<LinkedList<Integer>> players = new LinkedList<LinkedList<Integer>>();
    protected LinkedList<Integer> pack;

    public Card(int playerNumber) {
        if (playerNumber > 0) {
            this.playerNum = playerNumber;
        } else {
            this.playerNum = 1;
        }

        createPack(playerNumber);// all these actions should always happen in order
        // here we should save the file
        // since the program removes the cards when dealHands and dealDecks methods are
        // called
        // InputOutput in = new InputOutput(); // pack is full here

        setPlayers();
        setDecks();
        this.players = dealHands();
        this.decks = dealDecks();

        //Player player = new Player(this.decks, this.players);

        // InputOutput inn = new InputOutput();// pack is empty here

        // InputOutput in = new InputOutput();
    }

    public Card() {
        // default constructor
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
        try { // if fails due to the over load of the lists
            for (int i = 1; i <= (n * 2); i++) {
                for (int j = 1; j <= 4; j++)
                    pack.add(i);
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        Collections.shuffle(pack);
        return pack;
    }

    public int get1FromPack() {
        try {
            int card = this.pack.poll();
            return card;
        } catch (Exception e) {
            // e.printStackTrace();
            return -1;
        }
    }

    public void setPlayers() {
        this.players = new LinkedList<LinkedList<Integer>>();
        for (int i = 0; i < playerNum; i++) {
            this.players.add(new LinkedList<Integer>());
        }
    }

    public void setDecks() {
        this.decks = new LinkedList<LinkedList<Integer>>();
        for (int i = 0; i < playerNum; i++) {
            this.decks.add(new LinkedList<Integer>());
        }
    }

    public LinkedList<LinkedList<Integer>> dealHands() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < playerNum; j++) {
                LinkedList<Integer> playerN = this.players.get(j);
                int card = get1FromPack();
                if (card != -1) {
                playerN.add(card);
                }
            }
        }

        return this.players;
    }

    public LinkedList<LinkedList<Integer>> dealDecks() {

        int index = 0;
        while (!this.pack.isEmpty()) {
            LinkedList<Integer> deck = this.decks.get(index);
            int card = get1FromPack();
            if (card != -1) {
                deck.add(card);
            }
            index = (index + 1) % playerNum;
        }
        return decks;
    }

    public LinkedList<LinkedList<Integer>> getPlayers() {
        return this.players;
    }

    public LinkedList<LinkedList<Integer>> getDecks() {
        return this.decks;
    }
}