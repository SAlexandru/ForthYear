package goodfellas.TheGameOfLife.Cells;

public interface IReproductionListener {
    SCell findMate(SCell cell);

    void readyForReproduction(SCell sCell);
}
