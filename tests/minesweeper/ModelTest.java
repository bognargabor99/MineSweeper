package minesweeper;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

public class ModelTest {
	MineSweeperModel msm;
	
	@Before
	public void setUp() {
		msm = new MineSweeperModel();
	}

	@Test
	public void TestModel() {
		Assert.assertEquals(true, msm.OnBoard(5, 5));
		Assert.assertEquals(false, msm.OnBoard(-1, -1));
		Assert.assertEquals(false, msm.OnBoard(11, 11));
		for (int i = 0; i < msm.getMap().length; i++) 
			for (int j = 0; j < msm.getMap()[0].length; j++) {
				msm.boardClicked(i, j);
				if (msm.getMap()[i][j].getBomb()==Bomb.NO_BOMB)
					Assert.assertEquals(State.OPENED, msm.getMap()[i][j].getState());
			}
		
	}

}
