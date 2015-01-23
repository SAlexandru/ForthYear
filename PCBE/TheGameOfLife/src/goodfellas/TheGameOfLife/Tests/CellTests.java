package goodfellas.TheGameOfLife.Tests;

import static org.junit.Assert.*;
import goodfellas.TheGameOfLife.Cells.ASCell;
import goodfellas.TheGameOfLife.Cells.AbstractCell;
import goodfellas.TheGameOfLife.Cells.CellState;
import goodfellas.TheGameOfLife.Resources.FoodManager;
import goodfellas.TheGameOfLife.Resources.ReproductionManager;
import goodfellas.TheGameOfLife.Cells.SCell;
import goodfellas.TheGameOfLife.main.Main;

import org.junit.Test;

public class CellTests {

	@Test
	/**
	 * Tets if the application can be runned successfully
	 */
	public void testRunApplication()
	{
		String args[] = {"1","1","5"};
		Main.main(args);
	}
	
	@Test
	/**
	 * Tests if cells can be run successfully
	 */
	public void testRunCells()
	{
		AbstractCell asexualCell = new ASCell(1,1,new FoodManager());
		AbstractCell sexualCell = new SCell(1,1,new FoodManager(), new ReproductionManager());
		Thread[] threads = {new Thread(asexualCell), new Thread(sexualCell)};
		
		for (Thread t : threads) {
			t.start();
		}
		
		for (Thread t: threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	/**
	 * Tests if a cell is in ready to reproduce state
	 */
	public void testIsReadyForReproduce()
	{
		//initial state where a asexual cell will not be ready for reproduction
		AbstractCell asexualCell = new ASCell(1,1,new FoodManager());
		assertFalse(asexualCell.isReadyForReproduction());
		
		//initial state where a sexual cell will not be ready for reproduction
		AbstractCell sexualCell = new SCell(1,1,new FoodManager(), new ReproductionManager());
		assertFalse(sexualCell.isReadyForReproduction());
	}
	
	@Test
	/**
	 * Tests if the getState method returns the correct state
	 */
	public void testGetState()
	{
		//initial happy state for asexual cell
		AbstractCell asexualCell = new ASCell(1,1,new FoodManager());
		assertEquals(CellState.HUNGRY, asexualCell.getState());
		
		//initial happy state for sexual cell
		AbstractCell sexualCell = new SCell(1,1,new FoodManager(), new ReproductionManager());
		assertEquals(CellState.HUNGRY,sexualCell.getState());
	}

}
