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

public class InputOutput {


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

    InputOutput(LinkedList<LinkedList<Integer>> players) {
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
    
    InputOutput(LinkedList<Integer> winner, LinkedList<LinkedList<Integer>> players,
            LinkedList<LinkedList<Integer>> decks) {
        writeDeckContents(decks);
        writeEndGame(players, winner);
    }

    protected synchronized void writeEndGame(LinkedList<LinkedList<Integer>> players, LinkedList<Integer> winner) {
        if (players == null) {
            players = new LinkedList<>();
        }
        if (winner == null){
            winner = new LinkedList<>();
        }
        LinkedList<LinkedList<Integer>> allPlayers = new LinkedList<LinkedList<Integer>> (players);
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
                            "File " + "players/player" + (allPlayers.indexOf(player) + 1) + "_output.txt does not exists");
                }
            } catch (Exception e) {
                System.out.println("Error occurred whilst writing the winner information to file players/player"
                        + (allPlayers.indexOf(player) + 1)
                        + "_output.txt");
            }
            index ++;
        }
    }

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

    public InputOutput() {
    }

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
     * @see handToString(LinkedList)
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
            index ++;
        }
    }
}