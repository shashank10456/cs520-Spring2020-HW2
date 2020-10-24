package controller;
import model.RowGameModel;

public interface RowGameRulesStrategy {

    // Implementation should set the values of isLegalMove based on the current move indices
    public void handleLegalMoves(RowGameModel gameModel, int blockRowIndex, int blockColumnIndex);

    // Implementation should reset the game to be able to start and play again
    public void resetGame(RowGameModel gameModel, int numRows, int numCols);
}