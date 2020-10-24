package controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ThreeInARowTests {
	private RowGameController game;

	@Before
	public void setUp() {
		game = null;
		game = new RowGameController(3, 3, 1);
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
		int oldMovesLeft = game.gameModel.movesLeft;
		game.move(0, 2); // player 1 turn
		// 0,2 is a illegal move as 1,2 has not been played yet. So, content of this blockData should be "" and it should not be a valid legal move.
		assertEquals(game.gameModel.blocksData[0][2].getContents(), "");
		assertEquals(game.gameModel.blocksData[0][2].getIsLegalMove(), false);
		assertEquals(game.gameModel.player, "1");
		assertEquals(game.gameModel.movesLeft, oldMovesLeft); // since it is a illegal move movesLeft should be same
	}

	@Test
	public void testLegalMove() {
		int oldMovesLeft = game.gameModel.movesLeft;
		game.move(0, 0);
		// since 1,0 has been filled so now, 0,0 must be one of the valid legal positions.
		assertEquals(game.gameModel.blocksData[1][0].getContents(), "O");
		assertEquals(game.gameModel.blocksData[0][0].getContents(), "X");
		assertEquals(game.gameModel.player, "2");
		assertEquals(game.gameModel.movesLeft, oldMovesLeft - 1); // since it is a legal move movesLeft should be decreased by 1
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
		game.move(1, 2); // X
		game.move(1, 1); // O
		game.move(0, 1); // X
		game.move(0, 2); // O
		game.move(0, 0); // X
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
				if (row == 2) {
					assertEquals(game.gameModel.blocksData[row][column].getIsLegalMove(), true);
				} else {
					assertEquals(game.gameModel.blocksData[row][column].getIsLegalMove(), false);
				}
			}
		}

		assertEquals(game.gameModel.player, "1");
		assertEquals(game.gameModel.movesLeft, 9);
		assertEquals(game.gameModel.getFinalResult(), null);
	}

}