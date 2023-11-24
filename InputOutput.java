import java.util.Scanner;
import java.util.LinkedList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;

/**
 * @see InputOutput
 * 
 *      - InputOutput Class is responsible for every interaction the program has
 *      with terminal, and text files of players and decks, it gets the pack
 *      file and validates it and write it into a LinkedList, it also receives
 *      the number of players and validates it.
 *      This class will create player and deck files, delete the old ones and
 *      write to the new ones with the correct format mentioned in the
 *      specification.
 * 
 * @Note since creating, deleting and writing to files with threads could lead
 *       to serious compacts almost all methods inside this class are
 *       synchronized and the class could be counted as a thread safe class.
 * 
 * @author Amirali Famili
 */
public class InputOutput {

    /**
     * @see getPlayerNumber
     * 
     *      - getPlayerNumber is synchronized method, it asks for the number of
     *      players from the terminal and validates the number, if the input entered
     *      by the user is invalid it would recursively call it self until it
     *      receives the correct input.
     * 
     * @return the number of players the game should have
     */
    public synchronized int getPlayerNumber() {
        Scanner scan = new Scanner(System.in);

        System.out.print("\nPlease enter the number of players : ");
        String playerNum = scan.nextLine();

        try {
            int num = Integer.parseInt(playerNum);
            if (num < 1) {
                System.out.println("Please enter a positive Integer as player number: \n");
                return getPlayerNumber();
            } else {
                return num;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number as number of players : \n");
            return getPlayerNumber();
        }
    }

    /**
     * @see getPackFilePath
     * 
     *      - getPackFilePath is synchronized method, it asks for the location of
     *      the pack to load, if the path entered is invalid it would recursively
     *      ask the user to enter a valid path until it finds the file.
     * 
     * @Note this method is getting help form readInputPack method to put the file
     *       which contains pack into a LinkedList, the method will readInputPack
     *       will check the validity of the pack and if the pack is not in the right
     *       format it would recursively call this method again to get another valid
     *       pack file.
     * 
     * @return the pack which contains all the cards the game should be played with.
     */
    public synchronized LinkedList<Integer> getPackFilePath() {
        LinkedList<Integer> pack = new LinkedList<Integer>();
        System.out.print("\nPlease enter location of pack to load: ");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        Path path = Paths.get(filePath);

        if (Files.exists(path)) {
            pack = readInputPack(filePath);
        } else {
            System.out.println("Entered location is Not Valid !");
            getPackFilePath();
        }
        return pack;
    }

    /**
     * @see readInputPack
     * 
     *      - readInputPack is synchronized method, it receives a valid filePath and
     *      validates the content of that file to make sure that, the content of the
     *      file patch the requirements mentioned in the specification, then it
     *      would return the LinkedList pack created by the content of the file.
     * 
     * @Note if something goes wrong whilst adding the content of the file to the
     *       LinkedList, the code will assume there is something wrong with the pack
     *       and asks for another valid pack using getPackFilePath method.
     * 
     * @param filePath a valid file path which points to the location of the pack
     * 
     * @return the pack which contains all the cards the game should be played with.
     */
    public synchronized LinkedList<Integer> readInputPack(String filePath) {
        LinkedList<Integer> pack = new LinkedList<Integer>();

        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                int value = Integer.parseInt(line.trim());
                if (value < 0) {
                    continue;
                }
                pack.add(value);
            }
        } catch (Exception e) {
            System.out.println("Pack file does not have the right format please enter another file name");
            getPackFilePath();
        }
        return pack;
    }

    protected int playerNumber;
    private LinkedList<LinkedList<Integer>> players = new LinkedList<LinkedList<Integer>>();
    private final Object lock = new Object();

    /**
     * @see InputOutput
     * 
     *      - InputOutput (LinkedList<LinkedList<Integer>>) is the initial
     *      constructor of the InputOutput class, it's called inside the CardGame
     *      constructor at the start of the game, it assigns the players as well as
     *      player number, it also deletes the deck and player files and create new
     *      ones for as many player number as the game has.
     *      Then it would set the players initial hand which is the players
     *      LinkedList that has been passed down to it.
     * 
     * @Note this constructor is used for making an automated approach for setting
     *       players initial hand.
     * 
     * @param players the main players list at the start of the game which contains
     *                players initial hands and it's length represents the number of
     *                players.
     */
    public InputOutput(LinkedList<LinkedList<Integer>> players) {
        synchronized (lock) {
            this.players = players;
            this.playerNumber = players.size();
            deletePlayerFiles();
            deleteDeckFiles();
            createPlayerFiles();
            createDeckFiles();
            initialHand(this.players);
        }
    }

    public InputOutput() {
        // default constructor
    }

    /**
     * @see InputOutput
     * 
     *      - InputOutput ((LinkedList<Integer> , LinkedList<LinkedList<Integer>> ,
     *      LinkedList<LinkedList<Integer>> ) is a constructor of InputOutput class
     *      used at the end of the game, when the game is won their constructor is
     *      called and it informs other players that a player has won and it writes
     *      the decks final content into their corresponding files.
     * 
     * @param players the final state of the players list which contains all the
     *                player hands
     * @param decks   the final state of the decks list which contains all the
     *                player decks
     * @param winner  this list represents the hand of the player who has won the
     *                game.
     */
    public InputOutput(LinkedList<Integer> winner, LinkedList<LinkedList<Integer>> players,
            LinkedList<LinkedList<Integer>> decks) {
        writeDeckContents(decks);
        writeEndGame(players, winner);
    }

    /**
     * @see writeEndGame
     * 
     *      - writeEndGame(LinkedList<LinkedList<Integer>> , LinkedList<Integer> )
     *      is a synchronized void method, it takes the players list and the winner
     *      hand and write the necessary information for informing other players and
     *      their state in the game in players files.
     * 
     * @param players the final state of the players list which contains all the
     *                player hands
     * @param winner  this list represents the hand of the player who has won the
     *                game.
     */
    protected synchronized void writeEndGame(LinkedList<LinkedList<Integer>> players, LinkedList<Integer> winner) {
        if (players == null) {
            players = new LinkedList<>();
        }
        if (winner == null) {
            winner = new LinkedList<>();
        }
        LinkedList<LinkedList<Integer>> allPlayers = new LinkedList<LinkedList<Integer>>(players);
        LinkedList<Integer> Winner = new LinkedList<Integer>(winner);
        int index = 1;
        for (LinkedList<Integer> player : allPlayers) {
            try {
                File newFile = new File("players/player" + index + "_output.txt");
                if (newFile.exists()) {
                    try (FileWriter writer = new FileWriter(newFile, true)) {
                        if (player != Winner) {
                            writer.write("player " + (allPlayers.indexOf(Winner) + 1) + " has informed player "
                                    + index + " that player " + (allPlayers.indexOf(Winner) + 1)
                                    + " has won" + "\n");
                            writer.write("player " + index + " exits\n");
                            writer.write("player " + index + " final hand: " + cardsToString(player) + "\n");
                        } else {
                            writer.write("player " + index + " wins\n");
                            writer.write("player " + index + " exits\n");
                            writer.write("player " + index + " final hand: " + cardsToString(player) + "\n");
                        }

                    } catch (IOException e) {
                        System.out.println(
                                "Error occurred whilst writing the hand " + cardsToString(player) + " for player "
                                        + player);
                    }
                } else {
                    System.out.println(
                            "File " + "players/player" + (allPlayers.indexOf(player) + 1)
                                    + "_output.txt does not exists");
                }
            } catch (Exception e) {
                System.out.println("Error occurred whilst writing the winner information to file players/player"
                        + (allPlayers.indexOf(player) + 1)
                        + "_output.txt");
            }
            index++;
        }
    }

    /**
     * @see writeDrawsCard
     * 
     *      - writeDrawsCard(int, int)
     *      is a synchronized void method, receives an integer representing the card
     *      that has been drawn by the player and an integer which represents the
     *      player's index + 1, then it simply write a report to the text file
     *      informing the user that which player has draw what card from which deck.
     * 
     * @Note player's index + 1 is used directly to avoid calling the entire players
     *       list, it represents, player, player's file and their left deck.
     * @Note player and leftDeck both have the same index.
     * 
     * @param drawnCard is an integer which represents the card that a certain
     *                  player has drawn form their leftDeck
     * @param player    is an integer which represents the player's number, it's
     *                  file name and it's left deck
     */
    protected synchronized void writeDrawsCard(int drawnCard, int player) {
        try {
            File newFile = new File("players/player" + player + "_output.txt");
            if (newFile.exists()) {
                try (FileWriter writer = new FileWriter(newFile, true)) {
                    writer.write("player " + player + " draws a " + drawnCard + " from deck " + player + "\n");
                } catch (IOException e) {
                    System.out.println(
                            "Error occurred whilst writing the drawn card " + drawnCard + " for player " + player);
                }
            } else {
                System.out.println("File " + "players/player" + player + "_output.txt does not exists");
            }
        } catch (Exception e) {
            System.out.println("Error occurred whilst writing the player's drawn card to file players/player" + player
                    + "_output.txt");
            e.printStackTrace();
        }
    }

    /**
     * @see writeDiscardsCard
     * 
     *      - writeDiscardsCard(int, int)
     *      is a synchronized void method, receives an integer representing the card
     *      that has been discarded by the player and an integer which represents
     *      the
     *      player's index + 1, then it simply write a report to the text file
     *      informing the user that which player has discarded what card from which
     *      deck.
     * 
     * @Note player's index + 1 is used directly to avoid calling the entire players
     *       list, it represents, player, player's file and their left deck.
     * @Note player's right deck's index is player index + 1.
     * 
     * @param discardedCard is an integer which represents the card that a certain
     *                      player has discarded form their hand
     * @param player        is an integer which represents the player's number, it's
     *                      file name and it's left deck as well as right deck (is
     *                      index + 1)
     */
    protected synchronized void writeDiscardsCard(int discardedCard, int player) {
        try {
            File newFile = new File("players/player" + player + "_output.txt");
            if (newFile.exists()) {
                try (FileWriter writer = new FileWriter(newFile, true)) {
                    writer.write(
                            "player " + player + " discards a " + discardedCard + " from deck " + (player + 1) + "\n");
                } catch (IOException e) {
                    System.out.println(
                            "Error occurred whilst writing the discarded card " + discardedCard + " for player "
                                    + player);
                }
            } else {
                System.out.println("File " + "players/player" + player + "_output.txt does not exists");
            }
        } catch (Exception e) {
            System.out.println("Error occurred whilst writing the player's discarded card to file players/player"
                    + player + "_output.txt");
            e.printStackTrace();
        }
    }

    /**
     * @see writeCurrentHand
     * 
     *      - writeCurrentHand(LinkedList<Integer>, int)
     *      is a synchronized void method, it receives a LinkedList which is the
     *      player's hand and an integer which represents the player's number (index
     *      + 1 in players), then it would write the player's current hand inside
     *      the correct file which points to the player.
     * 
     * @Note player's index + 1 is used directly to avoid calling the entire players
     *       list, it represents, player, player's file and their left deck.
     * 
     * @param hand   is a LinkedList representing player's current hand in the game
     * @param player is an integer which represents the player's number, it's
     *               file name and it's left deck as well as right deck (is
     *               index + 1)
     */
    public synchronized void writeCurrentHand(LinkedList<Integer> hand, int player) {
        LinkedList<Integer> staticHand = new LinkedList<>(hand);
        if (staticHand != null) {
            try {
                File newFile = new File("players/player" + player + "_output.txt");
                if (newFile.exists()) {
                    try (FileWriter writer = new FileWriter(newFile, true)) {
                        writer.write("player " + player + " current hand is " + cardsToString(staticHand) + "\n");
                    } catch (IOException e) {
                        System.out.println(
                                "Error occurred whilst writing the hand " + cardsToString(staticHand) + " for player "
                                        + player);
                    }
                } else {
                    System.out.println("File " + "players/player" + player + "_output.txt does not exist");
                }
            } catch (Exception e) {
                System.out.println(
                        "Error occurred whilst writing the player's current hand to file players/player" + player
                                + "_output.txt");
                e.printStackTrace();
            }
        }
    }

    /**
     * @see writeDeckContents
     * 
     *      - writeDeckContents(LinkedList<LinkedList<Integer>>)
     *      is a synchronized void method, it receives a nested LinkedList which
     *      represents all the decks which exists in the game, then it would simply
     *      write the content of each nested list (deck) inside their own files.
     * 
     * @Note this method will only produce a single line of text inside each deck
     *       file which indicates the content of each list.
     * 
     * @param decks is a nested LinkedList which contains all the decks used inside
     *              the game at their final state.
     */
    private synchronized void writeDeckContents(LinkedList<LinkedList<Integer>> decks) {
        int index = 1;
        LinkedList<LinkedList<Integer>> finalDecks = new LinkedList<LinkedList<Integer>>(decks);
        for (LinkedList<Integer> deck : finalDecks) {
            try {
                File newFile = new File("decks/deck" + index + "_output.txt");
                if (newFile.exists()) {
                    try (FileWriter writer = new FileWriter(newFile, true)) {
                        if (!deck.isEmpty()) {
                            writer.write(
                                    "deck " + index + " contents " + cardsToString(deck)
                                            + "\n");
                        } else {
                            writer.write("deck " + index + " is Empty " + "\n");

                        }

                    } catch (IOException e) {
                        System.out.println(
                                "Error occurred whilst writing the final deck " + cardsToString(deck) + " for deck "
                                        + (finalDecks.indexOf(deck) + 1));
                    }
                } else {
                    System.out.println(
                            "File " + "decks/deck" + (finalDecks.indexOf(deck) + 1) + "_output.txt does not exists");
                }
            } catch (Exception e) {
                System.out.println(
                        "Error occurred whilst writing the final deck to file decks/deck" + index
                                + "_output.txt");
                e.printStackTrace();
            }
            index++;
        }
    }

    /**
     * @see createPlayerFiles
     * 
     *      - createPlayerFiles()
     *      is a synchronized void method, it creates player files for as many
     *      players as we have in each game, and it would leave them empty to be
     *      modified later.
     * 
     */
    public synchronized void createPlayerFiles() {
        for (int i = 1; i <= playerNumber; i++) {
            try {
                File newFile = new File("players/player" + i + "_output.txt");
                if (newFile.exists()) {
                    try (FileWriter writer = new FileWriter(newFile)) {
                        writer.write("");
                    } catch (IOException e) {
                        System.out.println("Error occurred while trying to clear file for player " + i);
                    }
                } else {
                    newFile.createNewFile();
                    System.out.println("File created: " + newFile.getName());
                }
            } catch (IOException e) {
                System.out.println("Error occurred while trying to create file for player " + i);
            }
        }
    }

    /**
     * @see createDeckFiles
     * 
     *      - createDeckFiles()
     *      is a synchronized void method, it creates deck files for as many
     *      decks (or players) as we have in each game, and it would leave them
     *      empty to be
     *      modified later.
     * 
     */
    public synchronized void createDeckFiles() {
        for (int i = 1; i <= playerNumber; i++) {
            try {
                File newFile = new File("decks/deck" + i + "_output.txt");
                if (newFile.exists()) {
                    try (FileWriter writer = new FileWriter(newFile)) {
                        writer.write("");
                    } catch (IOException e) {
                        System.out.println("Error occurred while trying to clear file for deck " + i);
                    }
                } else {
                    newFile.createNewFile();
                    System.out.println("File created: " + newFile.getName());
                }
            } catch (IOException e) {
                System.out.println("Error occurred while trying to create file for deck " + i);
            }
        }
    }

    /**
     * @see deleteDeckFiles
     * 
     *      - deleteDeckFiles()
     *      is a synchronized void method, it deletes all the deck files from
     *      previous run of the game.
     * 
     */
    public synchronized void deleteDeckFiles() {
        int counter = 1;
        do {
            File newFile = new File("decks/deck" + counter + "_output.txt");
            if (newFile.exists()) {
                try {
                    Files.delete(Paths.get("decks/deck" + counter + "_output.txt"));
                } catch (IOException e) {
                    System.out
                            .println("Error occurred while trying to delete file decks/deck" + counter + "_output.txt");
                }
                counter++;
            } else {
                break;
            }
        } while (true);
    }

    /**
     * @see deletePlayerFiles
     * 
     *      - deletePlayerFiles()
     *      is a synchronized void method, it deletes all the player files from
     *      previous run of the game.
     * 
     */
    public synchronized void deletePlayerFiles() {
        int counter = 1;
        do {
            File newFile = new File("players/player" + counter + "_output.txt");
            if (newFile.exists()) {
                try {
                    Files.delete(Paths.get("players/player" + counter + "_output.txt"));
                } catch (IOException e) {
                    System.out
                            .println("Error occurred while trying to delete file players/player" + counter
                                    + "_output.txt");
                }
                counter++;
            } else {
                break;
            }
        } while (true);
    }

    /**
     * @see handToString
     * 
     *      - handToString(LinkedList) is used for
     *      converting LinkedList element to a String,
     *      and then returning the String representation of the values.
     * 
     * @param hand a LinkedList consisting integers
     * 
     * @return the string representation of the values within the hand LinkedList
     */
    public synchronized String cardsToString(LinkedList<Integer> hand) {
        StringBuilder sHand = new StringBuilder();
        LinkedList<Integer> handCopy = new LinkedList<>(hand);
        for (Integer card : handCopy) {
            if (card != null) {
                sHand.append(card.intValue()).append(" ");
            } else {
                sHand.append(" ");
            }
        }
        return sHand.toString();
    }

    /**
     * @see initialHand
     * 
     *      - initialHand(LinkedList<LinkedList<Integer>>) is a synchronized void
     *      method, it receives the main players list at the start of the game and
     *      it would set the players initial hand for each player in the game in
     *      their corresponding files.
     * 
     */
    private synchronized void initialHand(LinkedList<LinkedList<Integer>> players) {
        int index = 1;
        for (LinkedList<Integer> player : players) {
            try (FileWriter writer = new FileWriter("players/player" + index + "_output.txt",
                    true)) {
                writer.write(
                        "player " + index + " initial hand " + cardsToString(player) + "\n");
            } catch (IOException e) {
                System.out.println("Error occurred while writing player's initial hand : " + player + " to file : "
                        + "players/player" + index + "_output.txt");
            }
            index++;
        }
    }
}