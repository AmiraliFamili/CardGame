import java.util.Scanner;
import java.util.LinkedList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;

public class InputOutput {

    public int getPlayerNumber() {
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

    public LinkedList<Integer> getPackFilePath() {
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

    public LinkedList<Integer> readInputPack(String filePath) {

        LinkedList<Integer> pack = new LinkedList<Integer>();

        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                int value = Integer.parseInt(line.trim());
                pack.add(value);
            }
        } catch (Exception e) {
            System.out.println("Pack file does not have the right format please enter another file name");
            getPackFilePath();
        }
        return pack;
    }

    private int playerNumber;
    private LinkedList<LinkedList<Integer>> players = new LinkedList<LinkedList<Integer>>();

    InputOutput(LinkedList<LinkedList<Integer>> players) { // run once
        this.players = players;
        this.playerNumber = players.size();
        deletePlayerFiles();
        deleteDeckFiles();
        createPlayerFiles();
        createDeckFiles();
        initialHand(players);
    }

    private int receivedCard;
    private int discardedCard;
    private LinkedList<Integer> hand = new LinkedList<Integer>();
    private LinkedList<Integer> leftDeck = new LinkedList<Integer>();
    private LinkedList<Integer> rightDeck = new LinkedList<Integer>();

    InputOutput(LinkedList<Integer> hand, int receivedCard, int discardedCard, int player) {
        this.hand = hand;
        this.receivedCard = receivedCard;
        this.discardedCard = discardedCard;
        writeDrawsCard(this.receivedCard, player);
        writeDiscardsCard(this.discardedCard, player);
        writeCurrentHand(this.hand, player);
    }

    InputOutput(LinkedList<Integer> winner, LinkedList<LinkedList<Integer>> players,
            LinkedList<LinkedList<Integer>> decks) {
        writeEndGame(players, winner);
        writeDeckContents(decks);
    }

    private void writeEndGame(LinkedList<LinkedList<Integer>> players, LinkedList<Integer> winner) {
        for (LinkedList<Integer> player : players) {
            try {
                File newFile = new File("players/player" + (players.indexOf(player) + 1) + "_output.txt");
                if (newFile.exists()) {
                    try (FileWriter writer = new FileWriter(newFile, true)) {
                        if (player != winner) {
                            writer.write("player " + (players.indexOf(winner) + 1) + " has informed player "
                                    + (players.indexOf(player) + 1) + " that player " + (players.indexOf(winner) + 1)
                                    + " has won" + "\n");
                            writer.write("player " + (players.indexOf(player) + 1) + " exits\n");
                            writer.write("player " + (players.indexOf(player) + 1) + " final hand: " + player + "\n");
                        } else {
                            writer.write("player " + (players.indexOf(player) + 1) + " wins\n");
                            writer.write("player " + (players.indexOf(player) + 1) + " exits\n");
                            writer.write("player " + (players.indexOf(player) + 1) + " final hand: " + player + "\n");
                        }

                    } catch (IOException e) {
                        System.out.println(
                                "Error occurred whilst writing the hand " + handToString(hand) + " for player "
                                        + player);
                    }
                } else {
                    System.out.println(
                            "File " + "players/player" + (players.indexOf(player) + 1) + "_output.txt does not exists");
                }
            } catch (Exception e) {
                System.out.println("Error occurred whilst writing the winner information to file players/player"
                        + (players.indexOf(player) + 1)
                        + "_output.txt");
            }
        }
    }

    private void writeDrawsCard(int drawnCard, int player) {
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
        }
    }

    private void writeDiscardsCard(int discardedCard, int player) {
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
        }
    }

    private void writeCurrentHand(LinkedList<Integer> hand, int player) {
        try {
            File newFile = new File("players/player" + player + "_output.txt");
            if (newFile.exists()) {
                try (FileWriter writer = new FileWriter(newFile, true)) {
                    writer.write("player " + player + " current hand is " + handToString(hand) + "\n");
                } catch (IOException e) {
                    System.out.println(
                            "Error occurred whilst writing the hand " + handToString(hand) + " for player " + player);
                }
            } else {
                System.out.println("File " + "players/player" + player + "_output.txt does not exists");
            }
        } catch (Exception e) {
            System.out.println("Error occurred whilst writing the player's current hand to file players/player" + player
                    + "_output.txt");
        }
    }

    private void writeDeckContents(LinkedList<LinkedList<Integer>> decks) {
        for (LinkedList<Integer> deck : decks) {
            try {
                File newFile = new File("decks/deck" + (decks.indexOf(deck) + 1) + "_output.txt");
                if (newFile.exists()) {
                    try (FileWriter writer = new FileWriter(newFile, true)) {
                        writer.write("deck " + (decks.indexOf(deck) + 1) + " contents " + handToString(deck) + "\n");
                    } catch (IOException e) {
                        System.out.println(
                                "Error occurred whilst writing the final deck " + handToString(deck) + " for deck "
                                        + (decks.indexOf(deck) + 1));
                    }
                } else {
                    System.out.println(
                            "File " + "decks/deck" + (decks.indexOf(deck) + 1) + "_output.txt does not exists");
                }
            } catch (Exception e) {
                System.out.println(
                        "Error occurred whilst writing the final deck to file decks/deck" + (decks.indexOf(deck) + 1)
                                + "_output.txt");
            }
        }
    }

    public InputOutput() {
    }

    public void createPlayerFiles() {
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

    public void createDeckFiles() {
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

    public void deleteDeckFiles() {
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

    public void deletePlayerFiles() {
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
    public String handToString(LinkedList<Integer> hand) {
        String sHand = "";
        if (hand.size() > 4) {
            // hand is in the wrong format
        }
        for (Integer card : hand) {
            if (card != null) {
                sHand = sHand + Integer.toString(card) + " ";
            } else {
                sHand = sHand + " ";
            }
        }
        return sHand;
    }

    private void initialHand(LinkedList<LinkedList<Integer>> players) {
        for (LinkedList<Integer> player : players) {
            try (FileWriter writer = new FileWriter("players/player" + (players.indexOf(player) + 1) + "_output.txt",
                    true)) {
                writer.write(
                        "player " + (players.indexOf(player) + 1) + " initial hand " + handToString(player) + "\n");
            } catch (IOException e) {
                System.out.println("Error occurred while writing player's initial hand : " + player + " to file : "
                        + "players/player" + (players.indexOf(player) + 1) + "_output.txt");
            }
        }
    }

    public static void main(String[] args) {
        InputOutput in = new InputOutput();
        in.playerNumber = 5;
        in.createPlayerFiles();
        in.createDeckFiles();
        in.deleteDeckFiles();
        in.deletePlayerFiles();
    }
}