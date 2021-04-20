package minesweeper;

import java.util.Comparator;

/**
 * A Comparator interfészt megvalósító osztály.
 * Record típusú adatokat hasonlít össze.
 * Pontszám szerint csökkenő sorrendbe rendez.
 */
public class PointComparator implements Comparator<Record> {

	/**
	 * Kötelezően megvalósított függvény, amely két rekordot pontszám szerint hasonlít össze.
	 */
	@Override
	public int compare(Record o1, Record o2) {
		return -Integer.compare(o1.getPoints(), o2.getPoints());
	}

}
