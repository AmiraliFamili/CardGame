import java.util.Collections;
import java.util.LinkedList;

/**
 * @see Card
 * 
 *      - Class Card is used for generating the required data for the main game
 *      to work, it's responsible for generating a pack and passing it's values
 *      to the main instances of this project which are players and decks which
 *      are later used in Player class.
 * 
 * @Note this class's main purpose is to generate hands and decks each in a 4
 *       card format by just receiving an integer.
 * 
 * @author Amirali Famili
 */
public class Card {

    // instances are set to be protected to be able to use them later in test
    // classes
    protected int playerNum;
    protected LinkedList<LinkedList<Integer>> decks = new LinkedList<LinkedList<Integer>>();
    protected LinkedList<LinkedList<Integer>> players = new LinkedList<LinkedList<Integer>>();
    protected LinkedList<Integer> pack;

    /**
     * @see Card(int)
     * 
     *      - Card(int) is the main constructor for Card class, it receives an
     *      integer which indicates the number of players, then it would create a
     *      pack of size 8 times player number.
     *      and then it deals the cards within that pack among players and decks.
     * 
     * @param playerNumber number of players which indicates the size of players and
     *                     decks list
     */
    public Card(int playerNumber) {
        if (playerNumber > 0) {
            this.playerNum = playerNumber;
        } else {
            this.playerNum = 1;
        }
        createPack(playerNumber);
        setPlayers(playerNumber);
        setDecks(playerNumber);

        this.players = dealHands();
        this.decks = dealDecks();
    }

    /**
     * @see Card()
     * 
     *      - Card() is the default constructor of the Card class, mainly used for
     *      manipulation of instances of this class from the corresponding test
     *      class.
     * 
     */
    public Card() {
        // default constructor
    }

    /**
     * @see emptyPack
     * 
     *      - emptyPack is a void method, used for emptying the pack by assigning it
     *      to a new LinkedList.
     * 
     */
    public void emptyPack() {
        this.pack = new LinkedList<Integer>();
    }

    /**
     * @see getPack
     * 
     *      - getPack is the getter method for pack, used for retrieving the pack.
     * 
     * @return pack
     */
    public LinkedList<Integer> getPack() {
        return this.pack;
    }

    /**
     * @see createPack
     * 
     *      - createPack creates a pack of size 8 times n which consists of integers
     *      from 1 to n*2 each repeated 4 times .
     *      , after the creating of such pack it uses Collections.shuffle to shuffle
     *      the pack in a random way.
     * 
     * @param n an integer to determine the size and content of the pack
     * 
     * @return pack
     */
    public LinkedList<Integer> createPack(int n) {
        if (this.pack == null) {
            emptyPack();
        }
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

    /**
     * @see get1FromPack
     * 
     *      - get1FromPack removes and returns the first element of the pack
     *      LinkedList, it will return -1 if an unexpected error happens
     *      whilst retrieving the element which is later handled by the program.
     * 
     * @return the first element of the pack (which also referred to as card in this
     *         game)
     */
    public int get1FromPack() {
        try {
            int card = this.pack.poll();
            return card;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * @see setPlayers
     * 
     *      - setPlayers is a void method, it assigns and sets players nested
     *      LinkedList, each player is represented by the index of their hand inside
     *      the
     *      nested linked list players, this method adds player number times empty
     *      LinkedLists to the players nested LinkedList.
     * 
     * @param playerNumber determines the length of the players list to be created
     */
    public void setPlayers(int playerNumber) {
        this.players = new LinkedList<LinkedList<Integer>>();
        for (int i = 0; i < playerNumber; i++) {
            this.players.add(new LinkedList<Integer>());
        }
    }

    /**
     * @see setDecks
     * 
     *      - setDecks is a void method, it assigns and sets decks nested
     *      LinkedList, each deck is represented by a LinkedList inside the
     *      nested linked list decks, this method adds player number times empty
     *      LinkedLists to decks.
     * 
     * @param playerNumber determines the length of the decks list to be created
     * 
     * @Note the left deck has the same index as player and right deck is on index
     *       above that
     */
    public void setDecks(int playerNumber) {
        this.decks = new LinkedList<LinkedList<Integer>>();
        for (int i = 0; i < playerNumber; i++) {
            this.decks.add(new LinkedList<Integer>());
        }
    }

    /**
     * @see dealHands
     * 
     *      - dealHands uses get1FromPack method to deal 1 card at a time to each
     *      player in the game, until each player's hand has 4 cards.
     * 
     */
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

    /**
     * @see dealDecks
     * 
     *      - dealDecks uses get1FromPack method to deal 1 card at a time to each
     *      deck in the game, until each deck has 4 cards and the pack is empty.
     * 
     */
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

    /**
     * @see getPlayers
     * 
     *      - getPlayers is the getter method for players, it retrieves the main
     *      players list which consists of all player hands in the game.
     * 
     * @return players, the main list for players hands
     */
    public LinkedList<LinkedList<Integer>> getPlayers() {
        return this.players;
    }

    /**
     * @see getDecks
     * 
     *      - getDecks is the getter method for decks, it retrieves the main decks
     *      list which consists of all player decks in the game.
     * 
     * @return decks, the main list for players decks
     */
    public LinkedList<LinkedList<Integer>> getDecks() {
        return this.decks;
    }
}