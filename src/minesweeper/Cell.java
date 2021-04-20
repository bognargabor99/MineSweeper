package minesweeper;

import java.io.Serializable;

/**
 * Cellát reprezentáló osztály, amely biztosítja a szerializálhatóságot.
 * Tárolja a cella jellemzőit és fel lehet őt fedni.
 */
public class Cell implements Serializable {
	private static final long serialVersionUID = -4130240748735757265L;
	/**
	 * A cella állapota (fedett, nem fedett, zászlós).
	 */
	private State state;
	/**
	 * Megmondja, hogy a cella tartalmaz-e bombát.
	 */
	private Bomb bomb;
	/**
	 * Megadja hány bomba van a körülötte lévő cellákban, ha ő maga bomba, akkor -1.
	 */
	private int mines;
	
	/**
	 * Konstruktor. Alapértékeket beállítja a cellán.
	 */
	public Cell() {
		this.bomb = Bomb.NO_BOMB;
		state = State.UNOPENED;
		mines = -1;
	}
	
	/**
	 * Visszaadja a cella körül lévő cellákban található bombák számát.
	 * @return Bombák száma
	 */
	public int getMines() {
		return mines;
	}
	
	/**
	 * Beállítja a cella körül lévő cellákban található bombák számát.
	 * @param m Bombák száma
	 */
	public void setMines(int m) {
		if (bomb==Bomb.BOMB || m > 8 || m < 0)
			return;
		mines = m;
	}
	
	/**
	 * Visszaadja, hogy bomba-e a cella vagy sem.
	 * @return Bomba vagy nem.
	 */
	public Bomb getBomb() {
		return bomb;
	}

	/**
	 * Beállítja, hogy a cellában legyen-e bomba.
	 * @param b Bomba vagy nem.
	 */
	public void setBomb(Bomb b) {
		this.bomb = b;
		mines = -1;
	}
	
	/**
	 * Beállítja a cella állapotát.
	 * @param s Cella állapota.
	 */
	public void setState(State s) {
		this.state = s;
	}
	
	/**
	 * Visszaadja a cella állapotát.
	 * @return Cella állapota.
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * Felfedi a mezőt
	 * @return A felfedés sikeressége
	 */
	public boolean Open() {
		if (state != State.UNOPENED)
			return false;
		state = State.OPENED;
		return true;
	}
}
