package minesweeper;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RecordTest {
	ArrayList<Record> records;
	
	@Before
	public void setUp() {
		records = new ArrayList<Record>();
	}
	
	@Test
	public void TestRecord() {
		Record r = new Record("Gabor", 345);
		Assert.assertEquals("Gabor", r.getName());
		Assert.assertEquals(345, r.getPoints());
	}

	@Test
	public void TestSort() {
		Record r1 = new Record("Gabor", 135);
		Record r2 = new Record("Player", 295);
		Record r3 = new Record("King Kong", 90);
		Record r4 = new Record("Pista", 170);
		
		records = new ArrayList<Record>();
		records.add(r1);
		records.add(r2);
		records.add(r3);
		records.add(r4);
		Collections.sort(records, new PointComparator());
		Assert.assertEquals("Player", records.get(0).getName());
		Assert.assertEquals(295, records.get(0).getPoints());
		Assert.assertEquals("Pista", records.get(1).getName());
		Assert.assertEquals(170, records.get(1).getPoints());
		Assert.assertEquals("Gabor", records.get(2).getName());
		Assert.assertEquals(135, records.get(2).getPoints());
		Assert.assertEquals("King Kong", records.get(3).getName());
		Assert.assertEquals(90, records.get(3).getPoints());
	}
}
