import java.util.LinkedList;

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
    public static void main(String[] args) {
        InputOutput obj = new InputOutput();
        int playerNumber = obj.getPlayerNumber();
        LinkedList<Integer> pack = obj.getPackFilePath();
        while (playerNumber * 6 >= pack.size()) {
            System.out.println("Your pack should have at least " + playerNumber * 6
                    + " cards inside it otherwise the game could not run");
            System.out.println("Current pack size : " + pack.size());
            System.out.println(
                    "Please either decrease the number of players or change the pack file to match the requirements of the game");

            playerNumber = obj.getPlayerNumber();
            pack = obj.getPackFilePath();
        }
        Card cardGame = new Card(playerNumber, pack);
        Player player = new Player(cardGame.getDecks(), cardGame.getPlayers());
        player.startGame();// main
    }
}
