public class Agent
{
    LStates[][] l_;

    int pX_ = 1;
    int pY_ = 1;
    int gNext_ = 0;

    public Agent(LStates[][] matrix)
    {
        l_ = new LStates[matrix.length][];
        for(int i = 0; i < matrix.length; i++)
            l_[i] = matrix[i].clone();
    }

    public void setInitialCoordinates(int x, int y)
    {
        pX_ = x;
        pY_ = y;
    }

    private void cNext()
    {
        if(l_[pX_ - 1][pY_ - 1] == LStates.WALL)
            gNext_ = 0;
        if(l_[pX_][pY_ - 1] == LStates.WALL)
            gNext_ = 1;
        if(l_[pX_ + 1][pY_ - 1] == LStates.WALL)
            gNext_ = 1;
        if(l_[pX_ + 1][pY_] == LStates.WALL)
            gNext_ = 2;
        if(l_[pX_ + 1][pY_ + 1] == LStates.WALL)
            gNext_ = 2;
        if(l_[pX_][pY_ + 1] == LStates.WALL)
            gNext_ = 3;
        if((l_[pX_ - 1][pY_ + 1] == LStates.WALL) && (l_[pX_][pY_ - 1] != LStates.WALL))
            gNext_ = 3;
        if((l_[pX_ - 1][pY_] == LStates.WALL) && (l_[pX_][pY_ - 1] != LStates.WALL))
            gNext_ = 0;
    }


    public void go()
    {
       for (int i = 0; i< 300; i++)
       {
          cNext();
           switch (gNext_)
           {
               case 0:
               {
                 
                 System.out.println("N");
                 pY_--;
                 break;
               }
               case 1:
               {
                   
                   System.out.println("E");
                   pX_++;
                   break;
               }
               case 2:
               {
                   
                   System.out.println("S");
                   pY_++;
                   break;
               }
               case 3:
               {
                   
                   System.out.println("W");
                   pX_--;
                   break;
               }
           }
       }
    }
}
