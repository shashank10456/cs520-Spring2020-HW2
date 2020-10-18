package controller;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import model.RowGameModel;
import view.RowGameGUI;


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
	public RowGameGUI gameView;
	public int numberOfRows;
	public int numberOfColumns;

	/**
	 * Creates a new game initializing the GUI.
	 */
	public RowGameController(int numRows, int numCols) {
		gameModel = new RowGameModel(numRows, numCols);
		gameView = new RowGameGUI(this, numRows, numCols);
		numberOfRows = numRows;
		numberOfColumns = numCols;
		resetGame();
	}

	public RowGameModel getModel() {
		return this.gameModel;
	}

	public RowGameGUI getView() {
		return this.gameView;
	}

	public void startUp() {
		gameView.gui.setVisible(true);
	}

	/**
	 * Returns the array of indices of the given block.
	 *
	 * @param block The block to be moved to by the current player
	 */
	public int[] getMatchingIndices(JButton block) {
		int[] matchingIndicesArray = new int[2];
		int flag = 1;
		for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
			for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
				if (block == gameView.gameBoardView.blocks[rowIndex][columnIndex]) {
					matchingIndicesArray[0] = rowIndex;
					matchingIndicesArray[1] = columnIndex;
					flag = 0;
					break;
				}
			}
			if (flag == 0) {
				break;
			}
		}
		return matchingIndicesArray;

	}

	/**
	 * Returns void
	 *
	 * This method handles the legal moves. It sets the legalMove based on the given/moved block.
	 *
	 * @param blockRowIndex row index of the selected block
	 * @param blockColumnIndex column index of the selected block
	 */
	public void handleLegalMoves(int blockRowIndex, int blockColumnIndex) {
		gameModel.blocksData[blockRowIndex][blockColumnIndex].setIsLegalMove(false);
		if (blockRowIndex > 0) {
			gameModel.blocksData[blockRowIndex - 1][blockColumnIndex].setIsLegalMove(true);
		}
	}

	/**
	 * Returns the content("X" or "O") of the corresponding block if the row and column indices are valid. Returns "invalid" if the indices are invalid
	 *
	 * This method handles the validation of the indices and returns the content based on the validation.
	 *
	 * @param blockRowIndex row index of the selected block
	 * @param blockColumnIndex column index of the selected block
	 */
	public String validateAndGetContent(int blockRowIndex, int blockColumnIndex) {

		if (blockRowIndex >= 0 && blockRowIndex < numberOfRows && blockColumnIndex >= 0 && blockColumnIndex < numberOfColumns) {
			return gameModel.blocksData[blockRowIndex][blockColumnIndex].getContents();
		}
		return "invalid";
	}

	/**
	 * Returns (1 or 2 or 0) based on if (player 1 is the winner or player 2 is the winner or no one has won yet) respectively.
	 *
	 * @param blockRowIndex row index of the selected block
	 * @param blockColumnIndex column index of the selected block
	 * @param numRows number of rows present in the game
	 * @param numColumns number of columns present in the game
	 */
	public int whoIsTheWinner(int blockRowIndex, int blockColumnIndex, int numRows, int numColumns) {

		for (int i = 0; i < numRows - 2; i++) {
			String tempString = (gameModel.blocksData[i][blockColumnIndex].getContents() + gameModel.blocksData[i + 1][blockColumnIndex].getContents() + gameModel.blocksData[i + 2][blockColumnIndex].getContents());
			if (tempString.equals("XXX")) {
				return 1;
			} else if (tempString.equals("OOO")) {
				return 2;
			}
		}

		for (int i = 0; i < numColumns - 2; i++) {
			String tempString = (gameModel.blocksData[blockRowIndex][i].getContents() + gameModel.blocksData[blockRowIndex][i + 1].getContents() + gameModel.blocksData[blockRowIndex][i + 2].getContents());
			if (tempString.equals("XXX")) {
				return 1;
			} else if (tempString.equals("OOO")) {
				return 2;
			}
		}

		String q1Diagonal = validateAndGetContent(blockRowIndex, blockColumnIndex) + validateAndGetContent(blockRowIndex - 1, blockColumnIndex + 1) + validateAndGetContent(blockRowIndex - 2, blockColumnIndex + 2);
		String q2Diagonal = validateAndGetContent(blockRowIndex, blockColumnIndex) + validateAndGetContent(blockRowIndex - 1, blockColumnIndex - 1) + validateAndGetContent(blockRowIndex - 2, blockColumnIndex - 2);
		String q3Diagonal = validateAndGetContent(blockRowIndex, blockColumnIndex) + validateAndGetContent(blockRowIndex + 1, blockColumnIndex - 1) + validateAndGetContent(blockRowIndex + 2, blockColumnIndex - 2);
		String q4Diagonal = validateAndGetContent(blockRowIndex, blockColumnIndex) + validateAndGetContent(blockRowIndex + 1, blockColumnIndex + 1) + validateAndGetContent(blockRowIndex + 2, blockColumnIndex + 2);
		if (q1Diagonal.equals("XXX") || q2Diagonal.equals("XXX") || q3Diagonal.equals("XXX") || q4Diagonal.equals("XXX")) {
			return 1;
		} else if (q1Diagonal.equals("OOO") || q2Diagonal.equals("OOO") || q3Diagonal.equals("OOO") || q4Diagonal.equals("OOO")) {
			return 2;
		}
		return 0;
	}


	/**
	 * Moves the current player into the given block.
	 *
	 * @param block The block to be moved to by the current player
	 */
	public void move(JButton block) {

		gameModel.movesLeft = gameModel.movesLeft - 1;
		String player = gameModel.player;
		int movesLeft = gameModel.movesLeft;

		int matchingIndicesArray[] = getMatchingIndices(block);
		int blockRowIndex = matchingIndicesArray[0];
		int blockColumnIndex = matchingIndicesArray[1];

		if (player.equals("1")) {
			gameModel.blocksData[blockRowIndex][blockColumnIndex].setContents("X");
			handleLegalMoves(blockRowIndex, blockColumnIndex);
			gameModel.player = "2";
			if (whoIsTheWinner(blockRowIndex, blockColumnIndex, numberOfRows, numberOfColumns) == 1) {
				gameModel.setFinalResult("Player 1 wins!");
				endGame();
			} else if (movesLeft == 0) {
				gameModel.setFinalResult(GAME_END_NOWINNER);
			}
		} else {
			// Check whether player 2 won
			gameModel.blocksData[blockRowIndex][blockColumnIndex].setContents("O");
			handleLegalMoves(blockRowIndex, blockColumnIndex);
			gameModel.player = "1";
			if (whoIsTheWinner(blockRowIndex, blockColumnIndex, numberOfRows, numberOfRows) == 2) {
				gameModel.setFinalResult("Player 2 wins!");
				endGame();
			} else if (movesLeft == 0) {
				gameModel.setFinalResult(GAME_END_NOWINNER);
			}
		}

		gameView.update(gameModel);

	}

	/**
	 * Ends the game disallowing further player turns.
	 */
	public void endGame() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				this.gameModel.blocksData[row][column].setIsLegalMove(false);
			}
		}

		gameView.update(gameModel);
	}

	/**
	 * Resets the game to be able to start playing again.
	 */
	public void resetGame() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				gameModel.blocksData[row][column].reset();
				// Enable the bottom row
				gameModel.blocksData[row][column].setIsLegalMove(row == numberOfRows - 1);
			}
		}
		gameModel.player = "1";
		gameModel.movesLeft = numberOfRows * numberOfColumns;
		gameModel.setFinalResult(null);

		gameView.update(gameModel);
	}
}