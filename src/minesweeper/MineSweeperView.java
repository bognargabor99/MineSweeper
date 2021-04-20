package minesweeper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Az MVC struktúrában a megjelenítő osztály. Csak a megjelenítéssel foglalkozik.
 */
public class MineSweeperView extends JFrame {
	private static final long serialVersionUID = 2L;
	
	/**
	 * Egy kép mérete a minesweeper.png-ben.
	 */
	private static final int imSiz = 27;
	private JPanel pointsPanel, board;
	private JMenuBar mineMenu;
	private JMenu file, newGame, scores;
	private JMenuItem easy, medium, hard, load, save, topList;
	private JLabel points;
	private BufferedImage mineImage;
	private Cell[][] mineMap;
	
	/**
	 * Konstruktor. Beállít alapértelmezett értékeket. Elhelyezi a paneleket és kialakítja a menü szerkezetét.
	 */
	public MineSweeperView() {
		board = new MineBoard();
		mineMap = new Cell[11][9];
		for (int i = 0; i < mineMap.length; i++) 
			for (int j = 0; j < mineMap[i].length; j++) 
				mineMap[i][j] = new Cell();
		try {
			mineImage = ImageIO.read(new File("src/minesweeper.png"));
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Image not found.");
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mineMenu = new JMenuBar();
		file = new JMenu("File");
		newGame = new JMenu("New Game");
		easy = new JMenuItem("Easy");
		medium = new JMenuItem("Medium");
		hard = new JMenuItem("Hard");
		load = new JMenuItem("Load");
		save = new JMenuItem("Save");
		scores = new JMenu("Scores");
		topList = new JMenuItem("Toplist");
		scores.add(topList);
		newGame.add(easy);
		newGame.add(medium);
		newGame.add(hard);
		file.add(newGame);
		file.add(load);
		file.add(save);
		mineMenu.add(file);
		mineMenu.add(scores);
		this.setJMenuBar(mineMenu);
		points = new JLabel("Points: 0");
		pointsPanel = new JPanel();
		pointsPanel.add(points);
		this.setResizable(false);
		this.add(pointsPanel, BorderLayout.NORTH);
		this.add(board, BorderLayout.CENTER);
		board.setPreferredSize(new Dimension(imSiz*11, imSiz*9));
		this.setVisible(true);
		this.setTitle("MineSweeper");
		this.pack();
	}
	
	/**
	 * Az új játék funkciókhoz tartozó eseménykezelőt állítja be.
	 * @param listenForNewGame Az eseménykezelő
	 */
	public void addNewGameListener(ActionListener listenForNewGame) {
		for (int i = 0; i < newGame.getItemCount(); i++) {
			this.newGame.getItem(i).addActionListener(listenForNewGame);
		}
	}
	
	/**
	 * A mentett játék betöltéséhez tartozó eseménykezelőt állítja be.
	 * @param listenForLoadGame Az eseménykezelő
	 */
	public void addLoadGameListener(ActionListener listenForLoadGame) {
		this.load.addActionListener(listenForLoadGame);
	}
	
	/**
	 * A mentett játék betöltéséhez tartozó eseménykezelőt állítja be.
	 * @param listenForSaveGame Az eseménykezelő
	 */
	public void addSaveGameListener(ActionListener listenForSaveGame) {
		this.save.addActionListener(listenForSaveGame);
	}
	
	/**
	 * Az aknamezőn való kattintáshoz tartozó eseménykezelőt állítja be.
	 * @param listenForClick Az eseménykezelő
	 */
	public void addBoardListener(MouseListener listenForClick) {
		this.board.addMouseListener(listenForClick);
	}
	
	/**
	 * A toplista megjelenítéséhez tartozó eseménykezelőt állítja be.
	 * @param listenForTopList Az eseménykezelő
	 */
	public void addTopListListener(ActionListener listenForTopList) {
		this.topList.addActionListener(listenForTopList);
	}
	
	/**
	 * Visszaadja egy kis cella méretét
	 * @return Cella mérete
	 */
	public static int getImgsize() {
		return imSiz;
	}
	
	/**
	 * Megkérdezi a felhasználó nevét és azt adja vissza.
	 * @return A felhasználó neve
	 */
	public String askForName() {
		return JOptionPane.showInputDialog("You won! Enter your name!");
	}

	/**
	 * Visszaadja a kirajzolt aknamezőt
	 * @return Az aknamező
	 */
	public Cell[][] getMineMap() {
		return mineMap;
	}

	/**
	 * Kirajzolja a paraméterként kapott aknamezőt, és kiírja a pontszámot.
	 * @param map Az aknamező
	 * @param pointsCount A pontszám
	 */
	public void viewBoard(Cell[][] map, int pointsCount) {
		mineMap = map;
		points.setText("Points: " + pointsCount);
		board.setPreferredSize(new Dimension(mineMap.length*imSiz, mineMap[0].length*imSiz));
		board.repaint();
		pack();
	}
	
	/**
	 * Megjeleníti a toplistát
	 * @param topList A toplista
	 */
	public void showTopList(ArrayList<Record> topList) {
		String list = "";
		for (int i = 0; i < Math.min(topList.size(), 10); i++) {
			list += (i+1) + ". " + topList.get(i).getName() + ": " + topList.get(i).getPoints() + " points\n";
		}
		JOptionPane.showMessageDialog(null, list, "Top list", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Egy belső panel osztály, amely kirajzoló metódusa arra való, hogy a tárolt aknamezőt kirajzolja.
	 */
	class MineBoard extends JPanel {		
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g) {
			board.removeAll();
			super.paint(g);
			for (int i = 0; i < mineMap.length; i++) {
				for (int j = 0; j < mineMap[i].length; j++) {
					switch (mineMap[i][j].getState()) {
					case UNOPENED:
						g.drawImage(mineImage.getSubimage(9*imSiz, 0, imSiz, imSiz), i*imSiz, j*imSiz, null);
						break;
					case FLAGGED:
						g.drawImage(mineImage.getSubimage(10*imSiz, 0, imSiz, imSiz), i*imSiz, j*imSiz, null);
						break;
					default:
						if (mineMap[i][j].getBomb()==Bomb.BOMB)
							g.drawImage(mineImage.getSubimage(11*imSiz, 0, imSiz, imSiz), i*imSiz, j*imSiz, null);
						else
							g.drawImage(mineImage.getSubimage(mineMap[i][j].getMines()*imSiz, 0, imSiz, imSiz), i*imSiz, j*imSiz, null);
						break;
					}
				}
			}
		}
	}
}
