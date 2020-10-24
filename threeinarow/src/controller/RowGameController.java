package controller;
import javax.swing.JButton;
import model.RowGameModel;

/**
 * Java implementation of the 3 in a row game, using the Swing framework.
 *
 * This quick-and-dirty implementation violates a number of software engineering
 * principles and needs a thorough overhaul to improve readability,
 * extensibility, and testability.
 */
public class RowGameController {
	public static final String GAME_END_NOWINNER = "Game ends in a draw";

	public RowGameModel gameModel;
	public int numberOfRows;
	public int numberOfColumns;
	public RowGameRulesStrategy gameRules;
	// Initialize the gameModel and the rules with which the game has to be played.
	public RowGameController(int numRows, int numCols, int rule) {
		gameModel = new RowGameModel(numRows, numCols);
		numberOfRows = numRows;
		numberOfColumns = numCols;
		if (rule == 1) {
			gameRules = new ThreeInARowRules();
		} else {
			gameRules = new TicTacToe();
		}
		gameRules.resetGame(gameModel, numRows, numCols);
	}

	public RowGameModel getModel() {
		return this.gameModel;
	}

	/**
	 * Returns the contents of the blocksData if the indices are valid, else returns "invalid"
	 *
	 * @param blockRowIndex The row index of the blocksData element of which we need the content
	 * @param blockColumnIndex The column index of the blocksData element of which we need the content
	 * @return The String value (the content of the valid blocksData element or "invalid")
	 */
	public String validateAndGetContent(int blockRowIndex, int blockColumnIndex) {

		if (blockRowIndex >= 0 && blockRowIndex < numberOfRows && blockColumnIndex >= 0 && blockColumnIndex < numberOfColumns) {
			return gameModel.blocksData[blockRowIndex][blockColumnIndex].getContents();
		}
		return "invalid";
	}

	/**
	 * Returns an integer value of (1,2,0) which corresponds to (player 1 has won, player 2 has won, no one has won yet)
	 *
	 * @param blockRowIndex The row index of the blocksData element
	 * @param blockColumnIndex The column index of the blocksData element
	 * @return The integer value of (1,2,0) which corresponds to (player 1 has won, player 2 has won, no one has won yet)
	 */
	public int whoIsTheWinner(int blockRowIndex, int blockColumnIndex) {
		// checking for the ("XXX" or "YYY") pattern vertically
		for (int i = 0; i < numberOfRows - 2; i++) {
			String tempString = (gameModel.blocksData[i][blockColumnIndex].getContents() + gameModel.blocksData[i + 1][blockColumnIndex].getContents() + gameModel.blocksData[i + 2][blockColumnIndex].getContents());
			if (tempString.equals("XXX")) {
				return 1;
			} else if (tempString.equals("OOO")) {
				return 2;
			}
		}
		// checking for the ("XXX" or "YYY") pattern horizontally
		for (int i = 0; i < numberOfColumns - 2; i++) {
			String tempString = (gameModel.blocksData[blockRowIndex][i].getContents() + gameModel.blocksData[blockRowIndex][i + 1].getContents() + gameModel.blocksData[blockRowIndex][i + 2].getContents());
			if (tempString.equals("XXX")) {
				return 1;
			} else if (tempString.equals("OOO")) {
				return 2;
			}
		}
		// checking for the ("XXX" or "YYY") pattern diagonally
		String q1Diagonal = validateAndGetContent(blockRowIndex, blockColumnIndex) + validateAndGetContent(blockRowIndex - 1, blockColumnIndex + 1) + validateAndGetContent(blockRowIndex - 2, blockColumnIndex + 2);
		String q2Diagonal = validateAndGetContent(blockRowIndex, blockColumnIndex) + validateAndGetContent(blockRowIndex - 1, blockColumnIndex - 1) + validateAndGetContent(blockRowIndex - 2, blockColumnIndex - 2);
		String q3Diagonal = validateAndGetContent(blockRowIndex, blockColumnIndex) + validateAndGetContent(blockRowIndex + 1, blockColumnIndex - 1) + validateAndGetContent(blockRowIndex + 2, blockColumnIndex - 2);
		String q4Diagonal = validateAndGetContent(blockRowIndex, blockColumnIndex) + validateAndGetContent(blockRowIndex + 1, blockColumnIndex + 1) + validateAndGetContent(blockRowIndex + 2, blockColumnIndex + 2);
		String q1Andq3Diagonal = validateAndGetContent(blockRowIndex, blockColumnIndex) + validateAndGetContent(blockRowIndex + 1, blockColumnIndex + 1) + validateAndGetContent(blockRowIndex - 1, blockColumnIndex - 1);
		String q2Andq4Diagonal = validateAndGetContent(blockRowIndex, blockColumnIndex) + validateAndGetContent(blockRowIndex + 1, blockColumnIndex - 1) + validateAndGetContent(blockRowIndex - 1, blockColumnIndex + 1);
		if (q1Diagonal.equals("XXX") || q2Diagonal.equals("XXX") || q3Diagonal.equals("XXX") || q4Diagonal.equals("XXX") || q1Andq3Diagonal.equals("XXX") || q2Andq4Diagonal.equals("XXX")) {
			return 1;
		} else if (q1Diagonal.equals("OOO") || q2Diagonal.equals("OOO") || q3Diagonal.equals("OOO") || q4Diagonal.equals("OOO") || q1Andq3Diagonal.equals("OOO") || q2Andq4Diagonal.equals("OOO")) {
			return 2;
		}
		return 0;
	}

	/**
	 * Handles the move/turn of the player and changes the corresponding values
	 *
	 * @param row The row index of the block
	 * @param column The column index of the block
	 */
	public void move(int row, int column) {
		int blockRowIndex = row;
		int blockColumnIndex = column;
		if (row >= 0 && row < numberOfRows && column >= 0 && column < numberOfColumns && gameModel.blocksData[row][column].getIsLegalMove() == false) {
			return;
		}
		gameModel.movesLeft = gameModel.movesLeft - 1;
		String player = gameModel.player;
		int movesLeft = gameModel.movesLeft;
		if (player.equals("1")) {
			// Check whether player 1 won
			gameModel.setContent(blockRowIndex, blockColumnIndex, "X");
			gameRules.handleLegalMoves(gameModel, blockRowIndex, blockColumnIndex);
			gameModel.setPlayer("2");
			if (whoIsTheWinner(blockRowIndex, blockColumnIndex) == 1) {
				gameModel.setFinalResult("Player 1 wins!");
				endGame();
			} else if (movesLeft == 0) {
				gameModel.setFinalResult(GAME_END_NOWINNER);
			}
		} else {
			// Check whether player 2 won
			gameModel.setContent(blockRowIndex, blockColumnIndex, "O");
			gameRules.handleLegalMoves(gameModel, blockRowIndex, blockColumnIndex);
			gameModel.setPlayer("1");
			if (whoIsTheWinner(blockRowIndex, blockColumnIndex) == 2) {
				gameModel.setFinalResult("Player 2 wins!");
				endGame();
			} else if (movesLeft == 0) {
				gameModel.setFinalResult(GAME_END_NOWINNER);
			}
		}
	}
	/**
	 * Ends the game disallowing further player turns.
	 */
	public void endGame() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				this.gameModel.setLegalMove(row, column, false);
			}
		}
	}
}