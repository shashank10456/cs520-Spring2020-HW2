package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.RowGameController;
import model.RowGameModel;


public class RowGameBoardView implements RowGameView {
    public JButton[][] blocks;
    public JPanel gamePanel = new JPanel(new FlowLayout());
    public int numberOfRows;
    public int numberOfColumns;
    public final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public RowGameBoardView(RowGameController gameController, int numRows, int numCols) {
        super();

        JPanel game = new JPanel(new GridLayout(numRows, numCols));
        gamePanel.add(game, BorderLayout.CENTER);
        blocks = new JButton[numRows][numCols];
        numberOfRows = numRows;
        numberOfColumns = numCols;
        // Initialize a JButton for each cell of the 3x3 game board.
        for (int row = 0; row < numRows; row++) {
            for (int column = 0; column < numCols; column++) {
                blocks[row][column] = new JButton();
                blocks[row][column].setPreferredSize(new Dimension(75, 75));
                game.add(blocks[row][column]);
                blocks[row][column].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        gameController.move((JButton) e.getSource());
                    }
                });
            }
        }
        
        this.pcs.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("call hogya bc");
				
			}
		});
    }

    
    /**
     * Updates the game view after the game model
     * changes state.
     *
     * @param gameModel The current game model
     */
    public void update(RowGameModel gameModel) {
        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfColumns; column++) {
                this.updateBlock(gameModel, row, column);
            } // end for col
        } // end for row	
    }

    /**
     * Updates the block view at the given row and column 
     * after the game model changes state.
     *
     * @param gameModel The game model
     * @param row The row that contains the block
     * @param column The column that contains the block
     */
    protected void updateBlock(RowGameModel gameModel, int row, int col) {
    	this.pcs.firePropertyChange("value", "sha", "amr");
        blocks[row][col].setText(gameModel.blocksData[row][col].getContents());
        blocks[row][col].setEnabled(gameModel.blocksData[row][col].getIsLegalMove());
    }
}