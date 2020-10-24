import controller.RowGameController;
import view.RowGameGUI;

import java.util.*;

public class RowGameApp {
    /**                                                                             
     * Starts a new game.
     */
    public static void main(String[] args) {

        int numberOfRows = 0;
        int numberOfColumns = 0;
        int rule = 1;

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of Rows: ");
        try {
            numberOfRows = sc.nextInt();
            System.out.print("Enter number of Columns: ");
            numberOfColumns = sc.nextInt();
            System.out.print("Enter 1 to play ThreeInARow or any other number to play TicTacToe: ");
            rule = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Please run the program again and enter natural numbers as values");
            return;
        } finally {

        }
        if ((numberOfRows <= 0) || (numberOfColumns <= 0)) {
            System.out.println("Please run the program again and enter natural numbers as values");
            return;
        }
        if ((numberOfRows < 3 && numberOfColumns < 3)) {
            System.out.println("Game ends in a draw!");
            return;
        }
        RowGameController game = new RowGameController(numberOfRows, numberOfColumns, rule);
        new RowGameGUI(game, numberOfRows, numberOfColumns);
        game.gameRules.resetGame(game.gameModel, numberOfRows, numberOfColumns);
    }
}