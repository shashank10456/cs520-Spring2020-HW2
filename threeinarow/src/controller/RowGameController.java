package controller;

import javax.swing.JButton;
import model.RowGameModel;

public class RowGameController {
	public static final String GAME_END_NOWINNER = "Game ends in a draw";

	public RowGameModel gameModel;
	public int numberOfRows;
	public int numberOfColumns;
	public RowGameRulesStrategy gameRules;

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
		String q1Andq3Diagonal = validateAndGetContent(blockRowIndex, blockColumnIndex) + validateAndGetContent(blockRowIndex + 1, blockColumnIndex + 1) + validateAndGetContent(blockRowIndex - 1, blockColumnIndex - 1);
		String q2Andq4Diagonal = validateAndGetContent(blockRowIndex, blockColumnIndex) + validateAndGetContent(blockRowIndex + 1, blockColumnIndex - 1) + validateAndGetContent(blockRowIndex - 1, blockColumnIndex + 1);
		if (q1Diagonal.equals("XXX") || q2Diagonal.equals("XXX") || q3Diagonal.equals("XXX") || q4Diagonal.equals("XXX") || q1Andq3Diagonal.equals("XXX") || q2Andq4Diagonal.equals("XXX")) {
			return 1;
		} else if (q1Diagonal.equals("OOO") || q2Diagonal.equals("OOO") || q3Diagonal.equals("OOO") || q4Diagonal.equals("OOO") || q1Andq3Diagonal.equals("OOO") || q2Andq4Diagonal.equals("OOO")) {
			return 2;
		}
		return 0;
	}


	public void move(int row, int column) {

		int blockRowIndex = row;
		int blockColumnIndex = column;

		if (row >= 0 && row < numberOfRows && column>=0 && column<numberOfColumns &&  gameModel.blocksData[row][column].getIsLegalMove() == false) {
			return;
		}
		gameModel.movesLeft = gameModel.movesLeft - 1;
		String player = gameModel.player;
		int movesLeft = gameModel.movesLeft;
		if (player.equals("1")) {
			gameModel.setContent(blockRowIndex, blockColumnIndex, "X");

			gameRules.handleLegalMoves(gameModel, blockRowIndex, blockColumnIndex);

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

			gameRules.handleLegalMoves(gameModel, blockRowIndex, blockColumnIndex);
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
}