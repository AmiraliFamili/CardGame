import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @see CardGame
 * 
 *      - CardGame Class is the main class which runs the game, it asks for the
 *      number of players and the location of a pack
 *      then passes those arguments ...
 * 
 * @Note ...
 * 
 * @author Amirali Famili
 */
public class CardGame {

    final private int timeSlice = 50;
    protected int playerNumber;
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
    public CardGame(int playerNumber, LinkedList<Integer> pack) {
        if (playerNumber > 0) {
            this.playerNumber = playerNumber;
        } else {
            this.playerNumber = 1;
        }
        this.pack = pack;
        setPlayers(this.playerNumber);
        setDecks(this.playerNumber);
        this.players = dealHands();
        this.decks = dealDecks();
        InputOutput output = new InputOutput(players);
        Player player = new Player(this.players);
        Card card = new Card(this.decks);
    }

    /**
     * @see CardGame()
     * 
     *      - CardGame() is the default constructor of the Card class, mainly used
     *      for
     *      manipulation of instances of this class from the corresponding test
     *      class.
     * 
     */
    public CardGame() {
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

    public void setPack(LinkedList<Integer> pack) {
        this.pack = pack;
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
        if (n < 1) {
            n = 1;
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
            for (int j = 0; j < playerNumber; j++) {
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
            index = (index + 1) % playerNumber;
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

    private volatile Boolean win = false;
    private final Object lock = new Object();
    private int counter = 0;

    /**
     * @see PlayerThread
     * 
     *      - PlayerThread is a nested class responsible for handling player
     *      threads, it extends Thread and has a run method in which it keeps
     *      executing a player turn until a player wins the game.
     * 
     * @Note playerThread is an inner class of Player class mainly responsible for
     *       multi threading of players and handling the threads to avoid any
     *       problems such as race condition
     */
    private class PlayerThread extends Thread {

        /**
         * @see run
         * 
         *      - run method for threading the players each player will use this run
         *      method to play the game from within their own thread.
         * 
         * @Note this method keeps executing player Turns until a player wins the game.
         */
        @Override
        public void run() {

            if (!players.get(0).isEmpty()) {
                while (!win) {
                    if (counter == -1) {
                        break;
                    }
                    playTurn();
                }
            } else {
                System.out.println("players have not been set please re run the game");
                System.exit(0);
            }
        }

        public synchronized void playTurn() {

            Card card = new Card(decks);
            Player player = new Player(players);

            try {
                card.getRightDeck((counter + 1) % playerNumber);
            } catch (IndexOutOfBoundsException e) {
                playTurn();
            }
            LinkedList<Integer> hand = player.getPlayer(counter);
            LinkedList<Integer> leftDeck = card.getLeftDeck(counter);
            LinkedList<Integer> rightDeck = card.getRightDeck(counter);

            synchronized (lock) {
                while (leftDeck.isEmpty()) {
                    try {
                        lock.wait(timeSlice);
                    } catch (InterruptedException e) {
                    }
                }

                int discard = player.getCard(hand);
                if (hand.contains(discard) && !leftDeck.isEmpty() && discard != 0) {// write methode
                    try {
                        int draw = leftDeck.poll();
                        Player turn = new Player(discard, draw, hand);// do this with methods instead of a constructor see if the speed improves 
                        rightDeck.add(discard);
                        InputOutput output = new InputOutput();
                        output.writeCurrentHand(hand, (counter % playerNumber) + 1);
                        output.writeDrawsCard(draw, (counter % playerNumber) + 1);
                        output.writeDiscardsCard(discard, (counter % playerNumber) + 1);
                        System.out.println("Round :  " + counter + " Player :  " + ((counter % playerNumber) + 1)
                                + " Hand :  " + hand);
                        win = playerWon(hand);
                    } catch (Exception e) {
                        try {
                            lock.wait(timeSlice);
                        } catch (Exception ee) {
                            // Thread.currentThread().interrupt();
                        }
                    }
                }
            }
            counter++;
        }
    }

    private static volatile boolean winnerDeclared = false;

    public synchronized boolean playerWon(LinkedList<Integer> player) {
        try {
            if (player.size() != 4) {
                return false;
            }
            LinkedList<Integer> hand = new LinkedList<Integer>(player);
            if (hand.equals(null) || hand.contains(null)) {
                return false;
            }
            LinkedList<LinkedList<Integer>> allPlayers = new LinkedList<LinkedList<Integer>>(Player.getPlayers());

            if (!winnerDeclared && allPlayers != null) {
                try {
                    synchronized (lock) {
                        if (allPlayers.contains(hand)) {
                            if (hand.get(0).equals(hand.get(1))
                                    && hand.get(1).equals(hand.get(2))
                                    && hand.get(2).equals(hand.get(3))) {
                                winnerDeclared = true;
                                int playerIndex = allPlayers.indexOf(hand);
                                if (playerIndex != -1) {
                                    System.out.println("Player_" + (playerIndex + 1) + " wins!");
                                    InputOutput output = new InputOutput(hand, Player.getPlayers(),
                                            Card.getDecks());
                                    this.win = true;
                                    counter = -1;
                                    System.exit(0);
                                } else {
                                    System.out.println("Error: Player not found in finalPlayers");
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void startGame() {
        ExecutorService executorService = Executors.newFixedThreadPool(players.size());

        for (int i = 0; i < players.size(); i++) {
            PlayerThread playerThread = new PlayerThread();
            executorService.execute(playerThread);
        }

        executorService.shutdown();

    }

    public static void main(String[] args) {
        InputOutput obj = new InputOutput();
        int playerNumber = obj.getPlayerNumber();
        LinkedList<Integer> pack = obj.getPackFilePath();

        while (playerNumber * 6 >= pack.size()) {
            System.out.println("Your pack should have at least " + playerNumber * 6
                    + " cards inside it otherwise the game could not run");
            System.out.println(
                    "Please either decrease the number of players or change the pack file to match the requirements of the game");

            playerNumber = obj.getPlayerNumber();
            pack = obj.getPackFilePath();
        }

        CardGame cardGame = new CardGame(playerNumber, pack);
        cardGame.startGame();// game starts
    }
}
