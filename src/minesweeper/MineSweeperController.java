package minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

/**
 * Az MVC sémában a Controllert valósítja meg, ő használja/vezérli a megjelenítőt és a modellt.
 */
public class MineSweeperController {
	/**
	 * A modell
	 */
	MineSweeperModel mineModel;	
	/**
	 * A megjelenítő
	 */
	MineSweeperView mineView;	
	
	/**
	 * Konstruktor. Elmenti a paraméterként kapott modellt és megjelenítőt,\n
	 * illetve a megjelenítőhöz adja a szükséges esemény vezérelt komponenseket.
	 * @param mineModel A modell
	 * @param mineView A megjelenítő
	 */
	public MineSweeperController(MineSweeperModel mineModel, MineSweeperView mineView) {
		this.mineModel= mineModel;
		this.mineView= mineView;
		
		mineView.addNewGameListener(new newGameListener());
		mineView.addLoadGameListener(new loadGameListener());
		mineView.addSaveGameListener(new saveGameListener());
		mineView.addBoardListener(new MineBoardAdapter());
		mineView.addTopListListener(new topListListener());
		mineView.viewBoard(mineModel.getMap(), mineModel.getPoints());
	}
	
	/**
	 * Belső osztály amely az aknamezőn való kattintást kezeli le.
	 */
	class MineBoardAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (mineModel.isEnded())
				return;
			if (SwingUtilities.isLeftMouseButton(e)) {
				mineModel.boardClicked(e.getX()/MineSweeperView.getImgsize(), e.getY()/MineSweeperView.getImgsize());
				mineView.viewBoard(mineModel.getMap(), mineModel.getPoints());
				if (mineModel.isEnded()) {
					mineModel.addToToplist(mineView.askForName());
				}
			}
			else if (SwingUtilities.isRightMouseButton(e)) {
				mineModel.flagMine(e.getX()/MineSweeperView.getImgsize(), e.getY()/MineSweeperView.getImgsize());
				mineView.viewBoard(mineModel.getMap(), mineModel.getPoints());
			}
		}		
	}
	
	/**
	 * Belső osztály, amely az új játékot indít, ha a játékos szeretné.
	 */
	class newGameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			mineModel.newGame(((JMenuItem)arg0.getSource()).getText());
			mineView.viewBoard(mineModel.getMap(), mineModel.getPoints());
		}
	}
	
	/**
	 * Belső osztály, amely használható, ha a játékos be szeretne tölteni egy elmentett játékállást.
	 */
	class loadGameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			mineModel.loadGame();
			mineView.viewBoard(mineModel.getMap(), mineModel.getPoints());
		}
	}
	
	/**
	 * Belső osztály, amely használható, ha a játékos el szeretné menteni az aktuális játékállást.
	 */
	class saveGameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			mineModel.saveGame();
		}
	}
	
	/**
	 * Belső osztály, amely használható, ha a játékos megszeretné tekinteni a toplistát.
	 */
	class topListListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			mineView.showTopList(mineModel.getTopList());
		}
	}
}
