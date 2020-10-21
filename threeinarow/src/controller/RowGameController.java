package controller;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import model.RowGameModel;
import view.RowGameGUI;

public class RowGameController {
	public static final String GAME_END_NOWINNER = "Game ends in a draw";

	public RowGameModel gameModel;
	public RowGameGUI gameView;
	public int numberOfRows;
	public int numberOfColumns;

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

	public void handleLegalMoves(int blockRowIndex, int blockColumnIndex) {
		gameModel.setLegalMove(blockRowIndex, blockColumnIndex, false);
		if (blockRowIndex > 0) {
			gameModel.setLegalMove(blockRowIndex - 1, blockColumnIndex, true);
		}
	}

	public String validateAndGetContent(int blockRowIndex, int blockColumnIndex) {

		if (blockRowIndex >= 0 && blockRowIndex < numberOfRows && blockColumnIndex >= 0 && blockColumnIndex < numberOfColumns) {
			return gameModel.blocksData[blockRowIndex][blockColumnIndex].getContents();
		}
		return "invalid";
	}

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


	public void move(JButton block, int row, int column) {

		gameModel.movesLeft = gameModel.movesLeft - 1;
		String player = gameModel.player;
		int movesLeft = gameModel.movesLeft;

		int blockRowIndex = row;
		int blockColumnIndex = column;

		if (player.equals("1")) {
			gameModel.setContent(blockRowIndex, blockColumnIndex, "X");

			handleLegalMoves(blockRowIndex, blockColumnIndex);

			gameModel.setPlayer("2");

			if (whoIsTheWinner(blockRowIndex, blockColumnIndex, numberOfRows, numberOfColumns) == 1) {
				gameModel.setFinalResult("Player 1 wins!");
				endGame();
			} else if (movesLeft == 0) {
				gameModel.setFinalResult(GAME_END_NOWINNER);
			}
		} else {
			// Check whether player 2 won
			gameModel.setContent(blockRowIndex, blockColumnIndex, "O");

			handleLegalMoves(blockRowIndex, blockColumnIndex);
			gameModel.setPlayer("1");
			if (whoIsTheWinner(blockRowIndex, blockColumnIndex, numberOfRows, numberOfColumns) == 2) {
				gameModel.setFinalResult("Player 2 wins!");
				endGame();
			} else if (movesLeft == 0) {
				gameModel.setFinalResult(GAME_END_NOWINNER);
			}
		}

	}


	public void endGame() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				this.gameModel.setLegalMove(row, column, false);
			}
		}
	}


	public void resetGame() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				gameModel.blocksData[row][column].reset();
				// Enable the bottom row
				gameModel.setLegalMove(row, column, row == numberOfRows - 1);
			}
		}
		gameModel.player = "1";
		gameModel.movesLeft = numberOfRows * numberOfColumns;
		gameModel.setFinalResult(null);

	}
}