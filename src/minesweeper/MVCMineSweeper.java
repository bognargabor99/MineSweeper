package minesweeper;

public class MVCMineSweeper {

	/**
	 * Main függvény.
	 */
	public static void main(String[] args) {
		MineSweeperModel mineModel = new MineSweeperModel();
		MineSweeperView mineView = new MineSweeperView();
		
		MineSweeperController mineController = new MineSweeperController(mineModel, mineView);
	}

}
