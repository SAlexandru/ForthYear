package goodfellas.TheGameOfLife.Resources;

import goodfellas.TheGameOfLife.Cells.IFoodListener;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class FoodManager implements IFoodListener {
	private Semaphore sema = new Semaphore((int)Math.ceil(Math.random() * 80 + 20), /*isFair*/ true);
    private Random random = new Random();
    
    public FoodManager() {
        System.out.println("\t\t\t\t\t\t\t Init amount " + sema.availablePermits());
    }

    @Override
    public boolean getFood(long tStarve) {
    	try {
			 return sema.tryAcquire(tStarve, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	finally {
    		System.out.println("\t\t\t\t\t\t\t Current amount(eat): " + sema.availablePermits());
    	}
    	return false;
    }

    @Override
    public void transformToFood() {
       sema.release(random.nextInt(4) + 1);
       System.out.println("\t\t\t\t\t\t\t Current amount(dead): " + sema.availablePermits());
    }

}
