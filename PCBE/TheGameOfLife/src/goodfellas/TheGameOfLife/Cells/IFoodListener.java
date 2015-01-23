package goodfellas.TheGameOfLife.Cells;

public interface IFoodListener {
    boolean getFood(long tStarve);
    void transformToFood();
}
