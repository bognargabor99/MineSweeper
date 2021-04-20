package minesweeper;

/**
 * Olyan osztály, amely egy fájlból beolvasott nevet és pontszámot tud tárolni egyben.\n
 * Megvalósítja a Comparable interfészt, így rendezhető is
 */
public class Record {
	/**
	 * A játékos neve
	 */
	private String name;
	/**
	 * A jatékos pontszáma
	 */
	private Integer points;
	
	/**
	 * Konstruktor
	 * @param n játékos neve
	 * @param p játékos pontszáma
	 */
	public Record(String n, Integer p) {
		this.name = n;
		this.points = p;
	}
	
	/**
	 * A j�t�kos nevét adja vissza
	 * @return Játékos neve
	 */
	public String getName() {
		return name;
	}

	/**
	 * A j�t�kos pontszámát adja vissza
	 * @return Játékos pontszáma
	 */
	public int getPoints() {
		return points;
	}
}
