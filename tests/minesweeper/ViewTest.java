package minesweeper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ViewTest {
	MineSweeperView msv;
	
	@Before
	public void setUp() {
		msv = new MineSweeperView();
		msv.setVisible(false);
	}
	
	@Test
	public void ViewTesting() {
		Assert.assertEquals(27, MineSweeperView.getImgsize());
		Cell[][] map = msv.getMineMap();
		Assert.assertEquals(11, map.length);
		Assert.assertEquals(9, map[0].length);
	}
}
