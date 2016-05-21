package client.login;

import client.base.*;
import client.misc.*;
import client.model.ModelFacade;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

import server.proxies.*;
import shared.game.map.Index;
import shared.game.player.Player;
//import com.google.gson.*;
//import com.google.gson.reflect.TypeToken;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController, Observer
{

	private IMessageView messageView;
	private IAction loginAction;
	private IServer server = ModelFacade.facadeCurrentGame.getServer();


	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) {

		super(view);
		this.messageView = messageView;
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		
		return loginAction;
	}

	@Override
	public void start() {
		
		getLoginView().showModal();
	}

	@Override
	public void signIn() {
		
		// TODO: log in user
		System.out.println("Logging in");
		String username1 = getLoginView().getLoginUsername();
		String password1 = getLoginView().getLoginPassword();
		if(server.loginUser(username1, password1).getResponseCode() == HttpURLConnection.HTTP_OK)
		{

			ModelFacade.facadeCurrentGame.loadGames();
			server.loginUser(username1, password1);
			// If log in succeeded
			getLoginView().closeModal();
			loginAction.execute();
		}
		else
		{
			getMessageView().setTitle("Login error");
			getMessageView().setMessage("Login failed - bad password or username.");
			getMessageView().showModal();
			return;
		}
		createPlayerFromCookie(username1, password1);
	}

	@Override
	public void register() {
		
		// TODO: register new user (which, if successful, also logs them in)
		String username = getLoginView().getRegisterUsername();
		String password = getLoginView().getRegisterPassword();
		String passrepeat = getLoginView().getRegisterPasswordRepeat();
		if(password.equals(passrepeat))
		{
			if(server.registerUser(username,password).getResponseCode()==HttpURLConnection.HTTP_OK)
			{

				System.out.println("i registered them");
				server.loginUser(username, password);
				getLoginView().closeModal();
				loginAction.execute();
				ModelFacade.facadeCurrentGame.loadGames();
			}
			else
			{
				getMessageView().setTitle("Registration error");
				getMessageView().setMessage("The username must be between 3 and 7 characters - " +
						"letters, digits, _, and/or -. The password must be 5 or more characters," +
						" and the two passwords must match.");
				getMessageView().showModal();
				return;
			}
			System.out.println("i failed to register");
		}
		else
		{
			getMessageView().setTitle("Registration error");
			getMessageView().setMessage("The username must be between 3 and 7 characters - " +
					"letters, digits, _, and/or -. The password must be 5 or more characters," +
					" and the two passwords must match.");
			getMessageView().showModal();
			return;
		}

		createPlayerFromCookie(username,password);
	}

	public void createPlayerFromCookie(String username, String password)
	{
		//Create player object from cookie info
		Player localplayer = new Player(null,null,null);
		String decodedCookie = URLDecoder.decode(server.loginUser(username, password).getUserCookie());
		String[] splitCookie = decodedCookie.split(",");
		for (int i = 0; i < splitCookie.length; i++)
		{
			String[] splitinfo = splitCookie[i].split(":");
			
			switch(i)
			{
			case 1:
				localplayer.setName(splitinfo[1]);
				break;
			case 2:
				break;
			case 3:
				splitinfo[1] = splitinfo[1].substring(0, splitinfo[1].length()-1);
				localplayer.setPlayerIndex(new Index(Integer.parseInt(splitinfo[1])));
				break;
			}
		}
		StringBuilder mybuilder=new StringBuilder();
		mybuilder.append(localplayer.getName());
		mybuilder.deleteCharAt(0);
		mybuilder.deleteCharAt(mybuilder.length()-1);
		mybuilder.setCharAt(0,Character.toUpperCase(mybuilder.charAt(0)));
		String newname=mybuilder.toString();
		//We MAY HAVE A SERIOUS BUG but not because of this.  Need to talk about the player name format.  We have different
		//format for names which is a problem!
		localplayer.setName(newname);
		ModelFacade.facadeCurrentGame.setLocalPlayer(localplayer);
	}
	
	@Override
	public void update(Observable arg0, Object arg1)
	{
		// TODO Auto-generated method stub
		
	}

}

