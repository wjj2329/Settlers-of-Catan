package server.persistence;

import javax.management.Descriptor;

/**
 * Created by williamjones on 6/7/16.
 */
public class PluginRegistry implements IFactory
{
    //Descrptor consits of name, class Name and descriptoin
    public void RegisterPlugin(Descriptor descriptor)
    {

    }
   public  Descriptor getAvailablePlugins()
   {
        return null;
   }
   public void createPlugin(Descriptor descriptor)
   {

   }
    public void LoadConfiguration()
    {

    }
    public void SaveConfiguration()
    {

    }

    @Override
    public IGameManager getGameManager() {
        return null;
    }

    @Override
    public IUserAccount getUserAccount() {
        return null;
    }
}
