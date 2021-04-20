package minesweeper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Az MVC struktúrában a modellt megvalósító osztály.\n
 * Nem ismeri a megjelenítőt. A játék szabályai alapján kezeli az aknamezőt.
 */
public class MineSweeperModel {
	/**
	 * Az aknamező
	 */
	private Cell[][] map;
	/**
	 * Az aknamezőn található bombák száma.
	 */
	private int mines;
	/**
	 * A játékos pontszáma.
	 */
	private int points;
	/**
	 * Konstruktor, amely egy új játékot indít könnyű módban
	 */
	public MineSweeperModel() {
		newGame("Easy");
	}
	
	/**
	 * Új játék indítása a megadott nehézséggel
	 * @param difficulty A nehézség
	 */
	public void newGame(String difficulty) {
		int columns, rows;
		points = 0;
		switch (difficulty) {
			case "Easy":
				columns = 11;
				rows = 9;
				mines = 12;
				break;
			case "Medium":
				columns = rows = 16;
				mines = 40;
				break;
			default:
				columns = 30;
				rows = 16;
				mines = 99;
				break;
		}
		map = new Cell[columns][rows];
		for (int i = 0; i < map.length; i++) 
			for (int j = 0; j < map[i].length; j++) 
				map[i][j] = new Cell();
		drawMines();
		setMineCount();
	}
	
	/**
	 * Bet�lt egy elmentett játékállást a savedGame.txt fájlból.
	 */
	public void loadGame() {
		try {
			ObjectInputStream oos = new ObjectInputStream(new FileInputStream("savedGame.txt"));
			map = (Cell[][]) oos.readObject();
			points = (int)oos.readObject();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Kimenti a játékállást a savedGame.txt-be, ha létezik már a fájl, akkor felülírja.
	 */
	public void saveGame() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savedGame.txt"));
			oos.writeObject(map);
			oos.writeObject(points);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Kisorsolja a bombák helyét az aknamezőn.
	 */
	public void drawMines() {
		Random r = new Random();
		int x = 0, y = 0; 
		for (int i = 0; i < mines; i++) {
			boolean sorsol = true;
	        while (sorsol) {
	            x = r.nextInt(map.length);
	            y = r.nextInt(map[x].length);
	            if (map[x][y].getBomb()==Bomb.NO_BOMB)
	                sorsol = false;
	        }
	        map[x][y].setBomb(Bomb.BOMB);
	    }
	}
	
	/**
	 * Beállítja az egyes celláknak, hogy mennyi bomba van a körülöttük lévő cellákban.
	 */
	public void setMineCount() {
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[i].length; j++) {
				map[i][j].setMines(BombsAround(i, j));
			}
	}
	
	/**
	 * Egy cellára zászlót rak, vagy ha már van rajta akkor leveszi azt.
	 * @param xCoord A cella x koordinátája
	 * @param yCoord A cella y koordinátája
	 */
	public void flagMine(int xCoord, int yCoord) {
		if (map[xCoord][yCoord].getState()==State.OPENED)
			return;
		if (map[xCoord][yCoord].getState()==State.UNOPENED)
			map[xCoord][yCoord].setState(State.FLAGGED);
		else 
			map[xCoord][yCoord].setState(State.UNOPENED);
	}
	
	/**
	 * Csak akkor hívjuk meg ha vége a játéknak. Felírja az adott játékos nevét a toplistára a pontjával együtt.\n
	 * Ha null-t vagy üres String-et kap, akkor nem csinál semmit.
	 * @param name A játékos neve
	 */
	public void addToToplist(String name) {
		if (name == null || name.equals(""))
			return;
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("topList.txt", true));
			pw.println(name + " " + points);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Szimulál egy klikkelést az aknamezőn. Ha nincs bomba az adott cellán és körülötte sem, akkor körülötte lévőket is felfedi.
	 * @param xCoord A cella x koordinátája
	 * @param yCoord A cella y koordinátája
	 */
	public void boardClicked(int xCoord, int yCoord) {
		if (isEnded() || !OnBoard(xCoord, yCoord))
			return;
		if (map[xCoord][yCoord].getBomb()==Bomb.BOMB && map[xCoord][yCoord].getState()==State.UNOPENED) {
			map[xCoord][yCoord].Open();
			points-=10;
			return;
		}
		if (map[xCoord][yCoord].getState()==State.OPENED)
			return;
		if (map[xCoord][yCoord].Open()) {
			points+=5;
			if (map[xCoord][yCoord].getMines()==0) {
				boardClicked(xCoord-1, yCoord-1);
				boardClicked(xCoord-1, yCoord);
				boardClicked(xCoord-1, yCoord+1);
				boardClicked(xCoord, yCoord-1);
				boardClicked(xCoord, yCoord+1);
				boardClicked(xCoord+1, yCoord-1);
				boardClicked(xCoord+1, yCoord);
				boardClicked(xCoord+1, yCoord+1);
			}
		}
	}
	
	/**
	 * Megadja, hogy a játék véget ért-e.
	 * @return Vége van-e a játéknak.
	 */
	public boolean isEnded() {
		for (int i = 0; i < map.length; i++) 
			for (int j = 0; j < map[i].length; j++) 
				if (map[i][j].getBomb()==Bomb.NO_BOMB && map[i][j].getState()==State.UNOPENED)
					return false;
		return true;
	}
	
	/**
	 * Visszaadja az aknamezőt (pl. majdani kirajzoláshoz)
	 * @return Az aknamező.
	 */
	public Cell[][] getMap() {
		return map;
	}
	
	/**
	 * Visszaadja a játékos pontszámát.
	 * @return A játékos pontszáma
	 */
	public int getPoints() {
		return points;
	}
	
	/**
	 * Beolvassa az elmentett toplistát egy ArrayList-be, rendezi és azt adja vissza.
	 * @return A toplista.
	 */
	public ArrayList<Record> getTopList() {
		try {
			ArrayList<Record> records = new ArrayList<Record>();
			BufferedReader br = new BufferedReader(new FileReader("topList.txt"));
			String str;
			while ((str = br.readLine()) != null) {
				String[] rec = str.split(" ");
				Record r = new Record(rec[0], Integer.parseInt(rec[1]));
				records.add(r);
			}
				
			br.close();
			Collections.sort(records, new PointComparator());
			return records;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Megadja, hogy egy cella szomszédaiban hány bomba van
	 * @param x A cella x koordinátája
	 * @param y A cella y koordinátája
	 * @return Bombák száma
	 */
	public int BombsAround(int x, int y) {
		int db = 0;
	    if (x > 0 && y > 0)
	        if (map[x-1][y-1].getBomb() == Bomb.BOMB) db++;
	    if (x > 0)
	        if (map[x-1][y].getBomb() == Bomb.BOMB) db++;
	    if (x > 0 && y < map[x-1].length-1)
	        if (map[x-1][y+1].getBomb() == Bomb.BOMB) db++;
	    if (y > 0)
	        if (map[x][y-1].getBomb() == Bomb.BOMB) db++;
	    if (y < map[x].length-1)
	        if (map[x][y+1].getBomb() == Bomb.BOMB) db++;
	    if (x < map.length-1 && y > 0)
	        if (map[x+1][y-1].getBomb() == Bomb.BOMB) db++;
	    if (x < map.length-1)
	        if (map[x+1][y].getBomb() == Bomb.BOMB) db++;
	    if (x < map.length-1 && y < map[x+1].length-1)
	        if (map[x+1][y+1].getBomb() == Bomb.BOMB) db++;
		return db;
	}
	
	/**
	 * Megadja, hogy a koordináták helyesek-e (ráesnek-e az aknamezőre)
	 * @param x Az x koordináta.
	 * @param y Az y koordináta.
	 * @return A koordináták helyessége.
	 */
	public boolean OnBoard(int x, int y) {
		return (x >= 0 && y >= 0 && x < map.length && y < map[0].length);
	}
}
