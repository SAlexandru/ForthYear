package goodfellas.TheGameOfLife.Resources;

import goodfellas.TheGameOfLife.Cells.IReproductionListener;
import goodfellas.TheGameOfLife.Cells.SCell;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class ReproductionManager implements IReproductionListener {
    private List<SCell> cells = new CopyOnWriteArrayList<>();
    
    @Override
    public SCell findMate(SCell cell) {
        cells.remove(cell);
        final long waitTime = (long)Math.ceil(Math.random() * 10 + 10);
        final Thread thisThread = Thread.currentThread();
        
        new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(waitTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				thisThread.interrupt();
			}}).start();
      
        while (!thisThread.isInterrupted()) {
        	if (cells.isEmpty()) {
        		continue;
        	}
        	if (!cells.get(0).isReadyForReproduction()) {
        		cells.remove(0);
        		continue;
        	}
        	return cells.get(0);
        }
        return null;
    }

    @Override
    public void readyForReproduction(SCell sCell) {
        cells.add(sCell);
    }

}
