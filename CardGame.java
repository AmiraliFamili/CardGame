/**
 * @see CardGame
 * 
 *      - CardGame Class is the main class which runs the game, it asks for the number of players and the location of a pack 
 *          then passes those arguments ...
 * 
 * @Note ... 
 * 
 * @author Amirali Famili
 */
public class CardGame {
    public static void main(String[] args) {
        InputOutput obj = new InputOutput();
        Card cardGame = new Card(obj.getInput());
        Player player = new Player(cardGame.getDecks(), cardGame.getPlayers());
        player.startGame();// main
    }
}
