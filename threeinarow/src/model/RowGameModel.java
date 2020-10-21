package model;


public class RowGameModel {
    public static final String GAME_END_NOWINNER = "Game ends in a draw";

    public RowBlockModel[][] blocksData;

    /**
     * The current player taking their turn
     */
    public String player = "1";
    public int movesLeft = 9;

    private String finalResult = null;


    public RowGameModel(int numRows, int numCols) {
        super();
        movesLeft = numRows * numCols;
        blocksData = new RowBlockModel[numRows][numCols];

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                blocksData[row][col] = new RowBlockModel(this);
            } // end for col
        } // end for row
    }

    public String getFinalResult() {
        return this.finalResult;
    }

    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }
}