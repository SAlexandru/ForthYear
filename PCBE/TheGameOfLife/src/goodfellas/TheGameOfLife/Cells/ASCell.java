package goodfellas.TheGameOfLife.Cells;

public class ASCell extends AbstractCell {

    public ASCell(long T_Full, long T_Starve, IFoodListener foodListener) {
        super(T_Full, T_Starve, foodListener);
    }

    public boolean reproduce() {

        if (isReadyForReproduction()) {
            (new Thread(new ASCell(getTFull(), getTStarve(), getFoodListener()))).run();
        }
        
         return true;

    }
}
