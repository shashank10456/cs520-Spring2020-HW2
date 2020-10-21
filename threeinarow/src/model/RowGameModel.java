package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RowGameModel {
    public static final String GAME_END_NOWINNER = "Game ends in a draw";

    public RowBlockModel[][] blocksData;

    /**
     * The current player taking their turn
     */
    public String player = "1";
    public int movesLeft = 9;

    private String finalResult = null;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

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
        String oldResult = this.finalResult;
        this.finalResult = finalResult;
        this.pcs.firePropertyChange("finalresult", oldResult, this.finalResult);
    }

    public void setLegalMove(int blockRowIndex, int blockColumnIndex, boolean isLegalMove) {
        boolean oldValue = this.blocksData[blockRowIndex][blockColumnIndex].getIsLegalMove();
        this.blocksData[blockRowIndex][blockColumnIndex].setIsLegalMove(isLegalMove);
        this.pcs.firePropertyChange("isLegalMove", oldValue, isLegalMove);
    }

    public void setContent(int blockRowIndex, int blockColumnIndex, String content) {
        // TODO Auto-generated method stub
        this.blocksData[blockRowIndex][blockColumnIndex].setContents(content);
        this.pcs.firePropertyChange("Content", "", content);
    }

    public void setPlayer(String player) {
        // TODO Auto-generated method stub
        String oldValue = this.player;
        this.player = player;
        this.pcs.firePropertyChange("player", oldValue, this.player);
    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        this.pcs.addPropertyChangeListener(propertyChangeListener);
    }
    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        this.pcs.removePropertyChangeListener(propertyChangeListener);
    }
}