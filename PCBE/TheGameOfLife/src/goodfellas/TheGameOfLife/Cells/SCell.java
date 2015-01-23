package goodfellas.TheGameOfLife.Cells;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SCell extends AbstractCell {
    private Random randomizer = new Random();
    private IReproductionListener reproductionListener;
    
    public SCell(long T_Full, long T_Starve, IFoodListener foodListener, IReproductionListener reproductionListener) {
        super(T_Full, T_Starve, foodListener);
        
        if (null == reproductionListener) {
            throw new IllegalArgumentException("reproductionListener must not be null");
        }
        
        this.reproductionListener = reproductionListener;
    }
    
    @Override
    public synchronized boolean reproduce() {

        List<AbstractCell> list = new ArrayList<AbstractCell>();
        list.add(new ASCell(getTFull(), getTStarve(), getFoodListener()));
        list.add(new SCell(getTFull(), getTStarve(), getFoodListener(), reproductionListener));

        if (isReadyForReproduction()) {
            reproductionListener.readyForReproduction(this);
            SCell mate = reproductionListener.findMate(this);

            if (null == mate) {
                return false;
            }
            
            (new Thread(list.get(randomizer.nextInt(list.size())))).run();
        }
        return true;
    }
}
