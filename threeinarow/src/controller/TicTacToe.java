package controller;

import model.RowGameModel;

public class TicTacToe implements RowGameRulesStrategy {

	@Override
	public void handleLegalMoves(RowGameModel gameModel, int blockRowIndex, int blockColumnIndex) {
		// TODO Auto-generated method stub
		gameModel.setLegalMove(blockRowIndex, blockColumnIndex, false);

	}

	@Override
	public void resetGame(RowGameModel gameModel, int numberOfRows, int numberOfColumns) {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				gameModel.blocksData[row][column].reset();
				// Enable the bottom row
				gameModel.setLegalMove(row, column, true);
			}
		}
		gameModel.player = "1";
		gameModel.movesLeft = numberOfRows * numberOfColumns;
		gameModel.setFinalResult(null);

	}

}