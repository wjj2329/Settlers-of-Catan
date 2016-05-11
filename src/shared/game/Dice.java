package shared.game;

import java.util.Random;

/**
 * Created by williamjones on 5/10/16.
 */
public class Dice
{
    public int roll()
    {
        Random random=new Random();
        int number=0;
        number+=random.nextInt(6)+1;
        number+=random.nextInt(6)+1;
        return number;
    }
}
