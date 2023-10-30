
import java.util.*;

/*
 * 
 * you can use super() to activate all classes simultaneously and revive the system's state 
 */
public class Card extends Player {
    final private int timeslice = 90;
    final private int deckNumber;

    protected LinkedList<LinkedList<Integer>> decks = new LinkedList<LinkedList<Integer>>();
    protected LinkedList<LinkedList<Integer>> players = new LinkedList<LinkedList<Integer>>();
    private LinkedList<Integer> pack;

    public Card(int playerNumber) {
        super(playerNumber);
        this.deckNumber = playerNumber;
        createPack(playerNumber);
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
        for (int i = 1; i <= (n*2); i++) {
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


    public void setPlayers(){
        for (int i = 0; i < deckNumber; i++){
            this.players.add(new LinkedList<Integer>());
        }
    }

    public void setDecks(){
        for (int i = 0; i < deckNumber; i++){
            this.decks.add(new LinkedList<Integer>());
        }
    }

    public LinkedList<LinkedList<Integer>> getPlayers(){
        return this.players;
    }

    public LinkedList<LinkedList<Integer>> dealHands(LinkedList<Integer> pack){

        for (int i = 0; i < 4 ; i++){
            for (int j = 0; j < deckNumber ; j++){
                LinkedList <Integer> playerN = this.players.get(j);
                int card = get1FromPack();
                playerN.add(card);
            }
        }
        
        return this.players;
    }

    public LinkedList<LinkedList<Integer>> dealDecks(LinkedList<Integer> pack){

        int index = 0;
        for (Integer card : pack){
            LinkedList<Integer> deck = this.decks.get(index);
            deck.add(card);
            index = (index + 1) % deckNumber;
        }
        return decks;
    }

    // try if you can get from the deck n+1 if something wen't wrong take from the first deck 

    private Boolean win = false;
    public void startGame(){
        int round = 0;
        while (!win) {
            LinkedList<Integer> player = this.players.get(round % deckNumber);
            LinkedList<Integer> insertTop = this.decks.get(round % deckNumber);
            LinkedList<Integer> discardBottom = this.decks.get(round + 1 % deckNumber);

            // round robin 
            // Threads
            // GUI 
            // get input from terminal ? 
            // should we write to the file here ? 
            // condition for the win 
            if (player.get(0) == player.get(1) && player.get(1)  == player.get(2) && player.get(2) == player.get(3)){win = true;}
            else{round ++;}

        }


    }


}