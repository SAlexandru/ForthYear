package goodfellas.TheGameOfLife.Cells;


public abstract class AbstractCell implements Runnable {
    private long          T_Full;
    private long          T_Starve;
    private int           timesItAte = 0;
    private CellState     cellState  = CellState.HUNGRY;
    private IFoodListener foodListener;

    protected AbstractCell(long T_Full, long T_Starve,
            IFoodListener foodListener) {
        if (T_Full <= 0) {
            throw new IllegalArgumentException(
                    "T_Full must be bigger than zero");
        }
        if (T_Starve <= 0) {
            throw new IllegalArgumentException(
                    "T_Starve must be bigger than zero");
        }

        if (null == foodListener) {
            throw new IllegalArgumentException("foodListener must not be null");
        }

        System.out.println(this);

        this.T_Full = T_Full;
        this.T_Starve = T_Starve;
        this.foodListener = foodListener;
    }

    public long getTFull() {
        return T_Full;
    }

    public long getTStarve() {
        return T_Starve;
    }

    public IFoodListener getFoodListener() {
        return foodListener;
    }

    public boolean isReadyForReproduction() {
        if (timesItAte >= 10) //never have sex when you're hungry
            return true;
        return false;
    }

    public CellState getState() {
        return cellState;
    }

    @Override
    public synchronized void run() {
        while (cellState != CellState.TERMINATED) {
            System.out.println("Now: " + Thread.currentThread().getId()
                    + "  @  " + cellState + " " + timesItAte);
            try {
                switch (cellState) {
                    case HUNGRY: {
                        cellState = CellState.DEAD;

                        if (foodListener.getFood(T_Starve)) {
                            ++timesItAte;
                            if (isReadyForReproduction()) {
                                cellState = CellState.REPRODUCE;
                            } else {
                                cellState = CellState.HAPPY;
                            }
                        }
                    }
                        break;

                    case HAPPY: {
                        wait(T_Full);
                        cellState = CellState.HUNGRY;
                    }
                        break;

                    case REPRODUCE: {
                        reproduce();
                        timesItAte = 0;
                        cellState = CellState.HUNGRY;
                    }
                        break;

                    case DEAD: {
                        foodListener.transformToFood();
                        cellState = CellState.TERMINATED;
                    }
                    default:
                        break;
                }

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

	public abstract boolean reproduce();
}
