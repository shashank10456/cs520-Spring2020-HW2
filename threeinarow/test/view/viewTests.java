package view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import controller.RowGameController;
import static org.junit.Assert.*;

public class viewTests {
	private RowGameController game;
	private RowGameBoardView gameView;
	@Before
	public void setUp() {
		game = null;
		game = new RowGameController(3, 3, 2);
		gameView = new RowGameBoardView(game, 3, 3);
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
	public void testUpdateBlockMethod() {
		gameView.updateBlock(game.gameModel, 0, 0);
		assertEquals(gameView.blocks[0][0].getText(), "");
		assertEquals(gameView.blocks[0][0].isEnabled(), true);
	}

	@Test
	public void testUpdateMethod() {
		gameView.update(game.gameModel);
		assertEquals(gameView.blocks[2][0].getText(), "X");
		assertEquals(gameView.blocks[2][0].isEnabled(), false);
		assertEquals(gameView.blocks[2][1].getText(), "O");
		assertEquals(gameView.blocks[2][1].isEnabled(), false);
		assertEquals(gameView.blocks[2][2].getText(), "X");
		assertEquals(gameView.blocks[2][2].isEnabled(), false);
		assertEquals(gameView.blocks[1][0].getText(), "O");
		assertEquals(gameView.blocks[1][0].isEnabled(), false);
	}

}