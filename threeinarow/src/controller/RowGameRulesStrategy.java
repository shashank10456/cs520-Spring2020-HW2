package controller;

import model.RowGameModel;


public interface RowGameRulesStrategy {
    public void handleLegalMoves(RowGameModel gameModel, int blockRowIndex, int blockColumnIndex);
    public void resetGame(RowGameModel gameModel, int numRows, int numCols);
}