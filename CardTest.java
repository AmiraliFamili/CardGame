import java.util.LinkedList;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;

public class CardTest {

    private String testEmptyPack() {
        Card card = new Card();
        Boolean phase1 = false;
        Boolean phase2 = false;

        card.createPack(5);
        LinkedList<Integer> pack = card.getPack();
        if (pack.size() == 5 * 8) {
            phase1 = true;
        } else {
            return "testEmptyPack Failed at phase1";
        }
        card.emptyPack();
        LinkedList<Integer> emptyPack = card.getPack();
        if (emptyPack.isEmpty()) {
            phase2 = true;
        } else {
            return "testEmptyPack Failed at phase1";
        }
        if (phase1 && phase2) {
            return "testEmptyPack Passes";
        } else {
            return "Unknown Error at testEmptyPack";
        }
    }

    private String testGetPack() {
        Card card = new Card();
        Boolean phase1 = false;
        Boolean phase2 = false;

        card.createPack(5);
        LinkedList<Integer> pack = card.getPack();

        if (pack.size() == 5 * 8) {
            phase1 = true;
        } else {
            return "testGetPack Failed at Phase1";
        }
        pack.removeFirst();
        LinkedList<Integer> pack2 = card.getPack();
        if (pack2.size() == (5 * 8) - 1) {
            phase2 = true;
        } else {
            return "testGetPack Failed at Phase2";
        }

        if (phase1 && phase2) {
            return "testGetPack Passes";
        } else {
            return "Unknown Error at testGetPack";
        }
    }

    private String testCreatePack() {
        Card card = new Card();

        Boolean phase1 = false;
        Boolean phase2 = false;
        Boolean phase3 = false;
        Boolean phase4 = false;

        try {
            card.createPack(1);
            phase1 = true;
            //System.out.println(card.getPack());
        } catch (Exception e) {
            return "testCreatePack Failed with Input 1";
        }
        card.emptyPack();
        try {
            card.createPack(0);
            phase2 = true;
            //System.out.println(card.getPack());
        } catch (Exception e) {
            return "testCreatePack Failed with Input 0";
        }
        card.emptyPack();
        try {
            card.createPack(-1);
            phase3 = true;
            //System.out.println(card.getPack());
        } catch (Exception e) {
            return "testCreatePack Failed with Input -1";
        }
        card.emptyPack();
        try {
            card.createPack(-1001);
            phase4 = true;
            //System.out.println(card.getPack());
        } catch (Exception e) {
            return "testCreatePack Failed with Input -1001";
        }

        if (phase1 && phase2 && phase3 && phase4) {
            return "testCreatePack Passes";
        } else {
            return "Unknown Error at testCreatePack";
        }
    }

    public String testGet1FromPack(){

        

        return "testGet1FromPack Passes";
    }

    public static void main(String[] args) {
        CardTest test = new CardTest();

        System.out.println(test.testEmptyPack());
        System.out.println(test.testGetPack());
        System.out.println(test.testCreatePack());

    }

}
