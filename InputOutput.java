import java.util.Scanner;

public class InputOutput {
    public int getInput() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter the number of players : \n");
        String playerNum = scan.nextLine();

        try {
            int num = Integer.parseInt(playerNum); 
            if (num < 1){
                System.out.println("Please enter a positive Integer as player number: \n");
                return getInput();
            }else{
                return num;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number as number of players : \n");
            return getInput(); 
        }
    }
}
