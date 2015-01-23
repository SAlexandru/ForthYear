package goodfellas.TheGameOfLife.Tests;

import static org.junit.Assert.*;
import goodfellas.TheGameOfLife.Resources.FoodManager;
import goodfellas.TheGameOfLife.Cells.IFoodListener;
import goodfellas.TheGameOfLife.Cells.IReproductionListener;
import goodfellas.TheGameOfLife.Resources.ReproductionManager;
import goodfellas.TheGameOfLife.Cells.SCell;

import org.junit.Test;

public class ResourceTests {

	@Test
	/**
	 * Tests if getFood() which returns the amount of food currently 
	 * available returns the value correctly
	 */
	public void testGetFood()
	{
		IFoodListener foodManager = new FoodManager();
		assertEquals(true,foodManager.getFood(1));
	}
	
	@Test
	/**
	 * Tests if finding a mate for a cell is succesfull
	 */
	public void testFindMate()
	{
		IReproductionListener reproductionManager = new ReproductionManager();
		SCell cell =  new SCell(1,1,new FoodManager(),reproductionManager);
		assertEquals(null, reproductionManager.findMate(cell));
	}
}
