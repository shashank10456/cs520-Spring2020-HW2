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


	/**
	 * Creates a new game initializing the GUI.
	 */
	public RowGameController() {
		gameModel = new RowGameModel();
		gameView = new RowGameGUI(this);

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
	 * Returns the indices of the given block.
	 *
	 * @param block The block to be moved to by the current player
	 */
	public int[] getMatchingIndices(JButton block) {
		int[] matchingIndicesArray = new int[2];
		int flag = 1;
		for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
			for (int columnIndex = 0; columnIndex < 3; columnIndex++) {
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
	 *This method handles the legal moves. It sets the legalMove based on the given/moved block.
	 *
	 * @param block The block to be moved to by the current player
	 */
	public void handleLegalMoves(RowGameModel gameModel, int blockRowIndex, int blockColumnIndex) {
		gameModel.blocksData[blockRowIndex][blockColumnIndex].setIsLegalMove(false);
		if (blockRowIndex > 0) {
			gameModel.blocksData[blockRowIndex - 1][blockColumnIndex].setIsLegalMove(true);
		}
	}

	public String validateAndGetContent(RowGameModel gameModel, int blockRowIndex, int blockColumnIndex, int numRows, int numColumns) {

		if (blockRowIndex >= 0 && blockRowIndex < numRows && blockColumnIndex >= 0 && blockColumnIndex < numColumns) {
			return gameModel.blocksData[blockRowIndex][blockColumnIndex].getContents();
		}
		return "invalid";
	}

	/**
	 * Returns void
	 *
	 *This method handles the legal moves. It sets the legalMove based on the given/moved block.
	 *
	 * @param block The block to be moved to by the current player
	 */
	public int whoIsTheWinner(RowGameModel gameModel, int blockRowIndex, int blockColumnIndex, int numRows, int numColumns) {

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

		String q1Diagonal = validateAndGetContent(gameModel, blockRowIndex, blockColumnIndex, 3, 3) + validateAndGetContent(gameModel, blockRowIndex - 1, blockColumnIndex + 1, 3, 3) + validateAndGetContent(gameModel, blockRowIndex - 2, blockColumnIndex + 2, 3, 3);
		String q2Diagonal = validateAndGetContent(gameModel, blockRowIndex, blockColumnIndex, 3, 3) + validateAndGetContent(gameModel, blockRowIndex - 1, blockColumnIndex - 1, 3, 3) + validateAndGetContent(gameModel, blockRowIndex - 2, blockColumnIndex - 2, 3, 3);
		String q3Diagonal = validateAndGetContent(gameModel, blockRowIndex, blockColumnIndex, 3, 3) + validateAndGetContent(gameModel, blockRowIndex + 1, blockColumnIndex - 1, 3, 3) + validateAndGetContent(gameModel, blockRowIndex + 2, blockColumnIndex - 2, 3, 3);
		String q4Diagonal = validateAndGetContent(gameModel, blockRowIndex, blockColumnIndex, 3, 3) + validateAndGetContent(gameModel, blockRowIndex + 1, blockColumnIndex + 1, 3, 3) + validateAndGetContent(gameModel, blockRowIndex + 2, blockColumnIndex + 2, 3, 3);
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
			handleLegalMoves(gameModel, blockRowIndex, blockColumnIndex);
			gameModel.player = "2";
			if (whoIsTheWinner(gameModel, blockRowIndex, blockColumnIndex, 3, 3) == 1) {
				gameModel.setFinalResult("Player 1 wins!");
				endGame();
			} else if (movesLeft == 0) {
				gameModel.setFinalResult(GAME_END_NOWINNER);
			}
		} else {
			// Check whether player 2 won
			gameModel.blocksData[blockRowIndex][blockColumnIndex].setContents("O");
			handleLegalMoves(gameModel, blockRowIndex, blockColumnIndex);
			gameModel.player = "1";
			if (whoIsTheWinner(gameModel, blockRowIndex, blockColumnIndex, 3, 3) == 2) {
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
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				this.gameModel.blocksData[row][column].setIsLegalMove(false);
			}
		}

		gameView.update(gameModel);
	}

	/**
	 * Resets the game to be able to start playing again.
	 */
	public void resetGame() {
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				gameModel.blocksData[row][column].reset();
				// Enable the bottom row
				gameModel.blocksData[row][column].setIsLegalMove(row == 2);
			}
		}
		gameModel.player = "1";
		gameModel.movesLeft = 9;
		gameModel.setFinalResult(null);

		gameView.update(gameModel);
	}
}