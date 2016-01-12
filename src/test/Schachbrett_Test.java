package test;

import static org.junit.Assert.*;
import javafx.geometry.Point2D;

import org.junit.Before;
import org.junit.Test;

import main_package.*;

public class Schachbrett_Test {

	private Game g;
	private String testStr;
	
	@Before
	public void setUp() throws Exception {
		g = new Game(new Player("Talip", true), new Player("Luise", false));
		testStr = "A6";
	}

	@Test
	public void testCoordConversion() {
		assertTrue(g.brett.realToDisplayCoord(g.brett.displayCoordToReal(testStr)).equals("A6"));
	}

}
