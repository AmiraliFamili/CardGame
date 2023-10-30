package pack;

import java.util.*;
/*
 * 
 * you can use super() to activate all classes simultaneously and revive the system's state 
 */

public class Card extends Player{
    final private int timeslice;
    private ArrayList<Integer> pack;

    public Card(int playerNum){
        super(playerNum);
    }

    public void emptyPack(){
        this.pack = new ArrayList<Integer>();
    }

    public ArrayList<Integer> getPack(){
        return this.pack;
    }

    public ArrayList<Integer> createPack(int n){
        if (this.pack == null){
            emptyPack();
        }
        Random random = new Random();
        for(int i = 1 ; i <= n ; i++){
            for(int j = 1 ; j <= 8; j++)
            pack.add(j);
        }
        Collections.shuffle(pack);
        return pack;
    }

    public int get1FromPack(){
        int card = this.pack.get(0);
        this.pack.remove(card);
        return card;
    }
    
}
