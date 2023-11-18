import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class cards extends Player {

    private LinkedList<LinkedList<Integer>> decks = new LinkedList<LinkedList<Integer>>();
    private int playerNumber;

    cards(LinkedList<LinkedList<Integer>> decks, LinkedList<LinkedList<Integer>> players){
        super(decks, players);
        this.decks = decks;
        this.playerNumber = players.size();
    }

     /**
     * @see hasDuplicates(LinkedList)
     * 
     *      - hasDuplicates(LinkedList) is a synchronized method for checking if a
     *      LinkedList of integers had duplicated values within it, .
     *      it returns true if it finds a duplicate and false if there is no element
     *      being repeated inside the LinkedList.
     * 
     * @param hand a LinkedList consisting integers
     * 
     * @return true or false
     */
    public synchronized boolean hasDuplicates(LinkedList<Integer> hand) {
        Set<Integer> dup = new HashSet<>();

        try {
            for (Integer card : hand) {
                if (!dup.add(card)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @see getCard(LinkedList)
     * 
     *      - getCard(LinkedList) is a synchronized method, it checks the LinkedList
     *      passed to it as an argument and it returns
     *      the first occurrence of an element that has not been repeated within
     *      hand with the help of hasDuplicate method.
     *      - this method returns the last element of the hand if it fails to find a
     *      duplicate,
     *      if there is a null value it returns 0 which reports a serious problem
     *      within the system.
     * 
     * 
     * @param hand a LinkedList consisting integers
     * 
     * @return an integer representing the card to be removed by the player
     */
    public synchronized int getCard(LinkedList<Integer> hand) {

        if (hand.contains(null) || hand.isEmpty()) {
            return 0;
        }

        if (hasDuplicates(hand)) {
            Map<Integer, Integer> cardCount = new HashMap<>();
            for (int card : hand) {
                cardCount.put(card, cardCount.getOrDefault(card, 0) + 1);
            }

            for (int card : hand) {
                if (cardCount.get(card) == 1) {
                    return card;
                }
            }
        }
        return hand.getLast();
    }
}
