package server.persistence;

import javax.management.Descriptor;

/**
 * Created by williamjones on 6/7/16.
 */
public class PluginRegistry implements IFactory
{
    //Descriptor consists of name, class Name and description
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

    public String pathToConfigFile()
    {
        return "Not yet implemented";
    }

    public Object getPlugin(String pluginName)
    {
        return "Not yet implemented";
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
