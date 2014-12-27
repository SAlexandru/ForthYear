public class Lab4
{
    static int height = 30;
    static int width = 30;

    public static void main(String[] args)
    {
        LayoutStates[][] matrix = new LayoutStates[height + 2][width + 2];

        // setting the borders of the layout
        for(int i = 0; i < width + 2; i++)
        {
            matrix[0][i] = LayoutStates.WALL;
            matrix[height+1][i] = LayoutStates.WALL;
        }
        for(int i = 0; i < height + 2; i++)
        {
            matrix[i][0] = LayoutStates.WALL;
            matrix[i][width+1] = LayoutStates.WALL;
        }

        Agent ag = new Agent(matrix);

        ag.setInitialCoordinates(1,1);
        ag.go();
    }
};
