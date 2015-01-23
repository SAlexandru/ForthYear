public class Lab4
{
    static int height = 30;
    static int width = 30;

    public static void main(String[] args)
    {
        LStates[][] m = new LStates[height + 2][width + 2];

        // setting the borders of the layout
        for(int i = 0; i < width + 2; i++)
        {
            m[0][i] = LStates.WALL;
            m[height+1][i] = LStates.WALL;
        }
        for(int i = 0; i < height + 2; i++)
        {
            m[i][0] = LStates.WALL;
            m[i][width+1] = LStates.WALL;
        }

        Agent ag = new Agent(m);

        ag.setInitialCoordinates(1,1);
        ag.go();
    }
};
