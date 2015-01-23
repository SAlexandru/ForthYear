package goodfellas.TheGameOfLife.main;

import goodfellas.TheGameOfLife.Cells.*;
import goodfellas.TheGameOfLife.Resources.FoodManager;
import goodfellas.TheGameOfLife.Resources.ReproductionManager;

public class Main {
    public static void main(String[] args) {
        if (3 != args.length) {
            System.out.println("Usage:");
            System.out.println("java Main T_Full T_Starve NumberOfInitialCells");
        }
        
        Thread[] threads;
        FoodManager fManager = new FoodManager();
        ReproductionManager rManager = new ReproductionManager();
        Integer T_Full, T_Starve, nrInitCells;
        
        T_Full = Integer.decode(args[0]);
        T_Starve = Integer.decode(args[1]);
        nrInitCells = Integer.decode(args[2]);
        threads = new Thread[nrInitCells];
        
        for (int i = 0; i < nrInitCells; ++i) {
            if (Math.random() > 0.5) {
                threads[i] = new Thread(new SCell(T_Full, T_Starve, fManager, rManager));
            }
            else {
                threads[i] = new Thread(new ASCell(T_Full, T_Starve, fManager));
            }
        }
        
        for (Thread t: threads) {
            t.start();
        }
        
        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
