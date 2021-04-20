package minesweeper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CellTest {
	Cell c1;
	
	@Before  
	public void setUp() {   
		c1 = new Cell();
	}
	
	@Test
	public void ConstructorTest() {
		Assert.assertEquals(State.UNOPENED, c1.getState());
		Assert.assertEquals(-1, c1.getMines());
		Assert.assertEquals(Bomb.NO_BOMB, c1.getBomb());
	}
	
	@Test
	public void SettersTest() {
		c1.setBomb(Bomb.BOMB);
		Assert.assertEquals(Bomb.BOMB, c1.getBomb());
		c1.setBomb(Bomb.NO_BOMB);
		Assert.assertEquals(Bomb.NO_BOMB, c1.getBomb());
		c1.setState(State.FLAGGED);
		Assert.assertEquals(State.FLAGGED, c1.getState());
		c1.setState(State.OPENED);
		Assert.assertEquals(State.OPENED, c1.getState());
		c1.setMines(10);
		Assert.assertEquals(-1, c1.getMines());
		c1.setMines(-2);
		Assert.assertEquals(-1, c1.getMines());
		c1.setMines(6);
		Assert.assertEquals(6, c1.getMines());
	}
	
	@Test
	public void CellOpenTest() {
		Assert.assertEquals(true, c1.Open());
		Assert.assertEquals(State.OPENED, c1.getState());
		c1.setState(State.FLAGGED);
		Assert.assertEquals(false, c1.Open());
		c1.setState(State.OPENED);
		Assert.assertEquals(false, c1.Open());
	}
}
