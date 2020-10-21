package controller;

import model.RowGameModel;

public class ThreeInARowRules implements RowGameRulesStrategy {

	@Override
	public void handleLegalMoves(RowGameModel gameModel, int blockRowIndex, int blockColumnIndex) {
		gameModel.setLegalMove(blockRowIndex, blockColumnIndex, false);
		if (blockRowIndex > 0) {
			gameModel.setLegalMove(blockRowIndex - 1, blockColumnIndex, true);
		}
	}

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