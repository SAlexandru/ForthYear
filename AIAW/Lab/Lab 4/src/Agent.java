public class Agent
{
    LayoutStates[][] layout;

    // current position
    int posX = 1;
    int posY = 1;
    // 0 - north, 1 - east, 2 - south, 3 - west; by default goes north;
    int goNext = 0;

    public Agent(LayoutStates[][] matrix)
    {
        layout = new LayoutStates[matrix.length][];
        for(int i = 0; i < matrix.length; i++)
            layout[i] = matrix[i].clone();
    }

    public void setInitialCoordinates(int x, int y)
    {
        posX = x;
        posY = y;
    }

    private void computeNext()
    {
        // checks all sides adjacent to the current position and changes direction whether there are obstacles or not
        if(layout[posX - 1][posY - 1] == LayoutStates.WALL)
            goNext = 0;
        if(layout[posX][posY - 1] == LayoutStates.WALL)
            goNext = 1;
        if(layout[posX + 1][posY - 1] == LayoutStates.WALL)
            goNext = 1;
        if(layout[posX + 1][posY] == LayoutStates.WALL)
            goNext = 2;
        if(layout[posX + 1][posY + 1] == LayoutStates.WALL)
            goNext = 2;
        if(layout[posX][posY + 1] == LayoutStates.WALL)
            goNext = 3;
        if((layout[posX - 1][posY + 1] == LayoutStates.WALL) && (layout[posX][posY - 1] != LayoutStates.WALL))
            goNext = 3;
        if((layout[posX - 1][posY] == LayoutStates.WALL) && (layout[posX][posY - 1] != LayoutStates.WALL))
            goNext = 0;
    }


    public void go()
    {
       // replace with for(;;) for an infinite loop
       for (int i = 0; i< 200; i++)
       {
          computeNext();
           switch (goNext)
           {
               case 0:
               {
                 // going north
                 System.out.println("North");
                 posY--;
                 break;
               }
               case 1:
               {
                   // going east
                   System.out.println("East");
                   posX++;
                   break;
               }
               case 2:
               {
                   // going south
                   System.out.println("South");
                   posY++;
                   break;
               }
               case 3:
               {
                   // going west
                   System.out.println("West");
                   posX--;
                   break;
               }
           }
       }
    }
}
