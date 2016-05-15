package testpckg;

import client.model.ModelFacade;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import shared.definitions.CatanColor;
import shared.game.CatanGame;
import shared.game.map.CatanMap;
import shared.game.map.Index;
import shared.game.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by williamjones on 5/12/16.
 */
public class TestingJsonImporterandExporter
{
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    ModelFacade myfacade=new ModelFacade();

    @Before
    public void setUp() throws Exception
    {
        CatanGame.singleton=new CatanGame();
        CatanGame.singleton.setMymap(new CatanMap(3));
        Map<Index, Player> myplayers=new HashMap<>();
        myplayers.put(new Index(0), new Player("william", CatanColor.BLUE,new Index(3)));
        myplayers.get(0).setPlayerIndex(new Index(0));
        CatanGame.singleton.setMyplayers(myplayers);
    }

   @Test
   public void test1()
   {
       try {
          JSONObject test= myfacade.serializeModel();
           System.out.println(test);
       } catch (JSONException e) {
           e.printStackTrace();
       }
   }


    @After
    public void tearDown() throws Exception
    {

    }
}
