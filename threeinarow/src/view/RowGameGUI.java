package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import model.RowGameModel;
import controller.RowGameController;


public class RowGameGUI implements RowGameView {
    private JFrame gui = new JFrame("Three in a Row");
    public RowGameBoardView gameBoardView;
    public JButton reset = new JButton("Reset");
    private RowGameStatusView gameStatusView;

    private RowGameController gameController;


    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameGUI(RowGameController gameController, int numRows, int numCols) {
        this.gameController = gameController;
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(new Dimension(1000, 750));
        gui.setResizable(true);

        gameBoardView = new RowGameBoardView(this.gameController, numRows, numCols);
        JPanel gamePanel = gameBoardView.gamePanel;

        JPanel options = new JPanel(new FlowLayout());
        options.add(reset);

        gameStatusView = new RowGameStatusView(this.gameController);
        JPanel messages = gameStatusView.messages;

        gui.add(gamePanel, BorderLayout.NORTH);
        gui.add(options, BorderLayout.CENTER);
        gui.add(messages, BorderLayout.SOUTH);
        gui.setVisible(true);
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameController.gameRules.resetGame(gameController.gameModel, numRows, numCols);
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
        gameBoardView.update(gameModel);

        gameStatusView.update(gameModel);
    }
}