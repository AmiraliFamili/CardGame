public class CardGame {
    public static void main(String[] args) {
        InputOutput obj = new InputOutput();
        Card cardGame = new Card(obj.getInput());
        Player player = new Player(cardGame.getDecks(), cardGame.getPlayers());
        player.startGame();// main
    }
}
