package controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TicTacToeTests {
	private RowGameController game;

	@Before
	public void setUp() {
		game = null;
		game = new RowGameController(3, 3, 2);
		game.move(2, 0); // content = "X"
		game.move(2, 1); // content = "O"
		game.move(2, 2); // content = "X"
		game.move(1, 0); // content = "O"
	}

	@After
	public void tearDown() {
		game = null;
	}

	@Test
	public void testIllegalMove() {
		int oldGameMovesLeft = game.gameModel.movesLeft;
		game.move(2, 1); // trying to overwrite "O" with "X"
		int newGameMovesLeft = game.gameModel.movesLeft;
		// 2,0 is a illegal move. It has already been used.
		assertEquals(oldGameMovesLeft, newGameMovesLeft);
		assertEquals(game.gameModel.player, "1");
		assertEquals(game.gameModel.blocksData[2][1].getContents(), "O"); // Content should not be overwritten

	}

	@Test
	public void testLegalMove() {
		game.move(0, 2); // This is a legal move in TicTacToe but a illegal  move in ThreeInArow game.
		assertEquals(game.gameModel.blocksData[0][2].getContents(), "X");
	}

	@Test
	public void player1Win() {
		game.move(1, 1); // X
		game.move(1, 2); // O
		game.move(0, 0); // X
		// Clearly player 1 should be the winner
		assertEquals(game.whoIsTheWinner(0, 0), 1);
		assertEquals(game.gameModel.getFinalResult(), "Player 1 wins!");
	}

	@Test
	public void drawMatchTest() {
		game.move(0, 0); // X
		game.move(0, 2); // O
		game.move(0, 1); // X
		game.move(1, 1); // O
		game.move(1, 2); // X
		// Clearly the game is a tie
		assertEquals(game.whoIsTheWinner(0, 0), 0);
		assertEquals(game.gameModel.getFinalResult(), "Game ends in a draw");
	}

	@Test
	public void resetGame() {
		game.gameRules.resetGame(game.gameModel, 3, 3);

		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				// Enable the bottom row
				assertEquals(game.gameModel.blocksData[row][column].getContents(), "");
				assertEquals(game.gameModel.blocksData[row][column].getIsLegalMove(), true);
			}
		}
		assertEquals(game.gameModel.player, "1");
		assertEquals(game.gameModel.movesLeft, 9);
		assertEquals(game.gameModel.getFinalResult(), null);
	}

}