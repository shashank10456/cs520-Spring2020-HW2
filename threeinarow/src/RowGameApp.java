import controller.RowGameController;
import view.RowGameGUI;

import java.util.*;  

public class RowGameApp {
    /**                                                                             
     * Starts a new game in the GUI.
     */
    public static void main(String[] args) {
    	
    	Scanner sc= new Scanner(System.in);    //System.in is a standard input stream  
    	System.out.print("Enter number of Rows- ");  
    	int numberOfRows= sc.nextInt();  
    	System.out.print("Enter number of Columns- ");  
    	int numberOfColumns= sc.nextInt();  
    	
        RowGameController game = new RowGameController(numberOfRows, numberOfColumns);
        new RowGameGUI(game, numberOfRows, numberOfColumns);
        game.resetGame();
    }
}