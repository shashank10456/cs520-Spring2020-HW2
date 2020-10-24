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

    /**
     * Sets the content of this.finalResult to finalResult(param) and then fires an event.
     *
     * @param finalResult The new value for this.finalResult
     */
    public void setFinalResult(String finalResult) {
        String oldResult = this.finalResult;
        this.finalResult = finalResult;
        this.pcs.firePropertyChange("finalresult", oldResult, this.finalResult);
    }

    /**
     * Sets the content of corresponding blocksdata element to isLegalMove(param) and then fires an event.
     *
     * @param blockRowIndex The row index of the element of blocksData
     * @param blockColumnIndex The column index of the element of blocksData
     * @param isLegalMove The new value
     */
    public void setLegalMove(int blockRowIndex, int blockColumnIndex, boolean isLegalMove) {
        boolean oldValue = this.blocksData[blockRowIndex][blockColumnIndex].getIsLegalMove();
        this.blocksData[blockRowIndex][blockColumnIndex].setIsLegalMove(isLegalMove);
        this.pcs.firePropertyChange("isLegalMove", oldValue, isLegalMove);
    }

    /**
     * Sets the content of corresponding blocksdata element and then fires an event.
     *
     * @param blockRowIndex The row index of the element of blocksData
     * @param blockColumnIndex The column index of the element of blocksData
     * @param content The new value
     */
    public void setContent(int blockRowIndex, int blockColumnIndex, String content) {
        // TODO Auto-generated method stub
        this.blocksData[blockRowIndex][blockColumnIndex].setContents(content);
        this.pcs.firePropertyChange("Content", "", content);
    }

    /**
     * Sets this.player based on the player(param) and then fires an event.
     *
     * @param player The new value for this.player
     */
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