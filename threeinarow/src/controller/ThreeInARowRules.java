package controller;

import model.RowGameModel;

public class ThreeInARowRules implements RowGameRulesStrategy {

	/**
	 * sets the values of isLegalMove based on the move
	 *
	 * @param gameModel The gameModel that will contain the data
	 * @param blockRowIndex The row index of the element of blocksData
	 * @param blockColumnIndex The column index of the element of blocksData
	 */
	@Override
	public void handleLegalMoves(RowGameModel gameModel, int blockRowIndex, int blockColumnIndex) {
		gameModel.setLegalMove(blockRowIndex, blockColumnIndex, false);
		if (blockRowIndex > 0) {
			gameModel.setLegalMove(blockRowIndex - 1, blockColumnIndex, true);
		}
	}

	/**
	 * Resets the game to be able to start and play again
	 *
	 * @param gameModel The gameModel that will contain the data
	 * @param numberOfRows Number of rows in the grid/game
	 * @param numberOfColumns Number of columns in the grid/game
	 */
	@Override
	public void resetGame(RowGameModel gameModel, int numberOfRows, int numberOfColumns) {
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