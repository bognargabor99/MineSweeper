package minesweeper;

/**
 * Egy olyan enumeráció, amely egy cella állapotát tartalmazza,\n
 */
public enum State {
	/**
	 * felfedett
	 */
	OPENED, 
	/**
	 * zázlóval megjelölt
	 */
	FLAGGED, 
	/**
	 * érintetlen
	 */
	UNOPENED
}
