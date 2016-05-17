package client.login;

import client.base.*;
import client.misc.*;
import client.model.ModelFacade;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

import server.proxies.*;
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
	private IServer server = new ServerProxy();
	
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
		String username = getLoginView().getLoginUsername();
		String password = getLoginView().getLoginPassword();
		if(server.loginUser(username, password).getResponseCode() == HttpURLConnection.HTTP_OK)
		{		
			// If log in succeeded
			getLoginView().closeModal();
			loginAction.execute();
		}
		
		//Get ID from cookie
		ModelFacade.facace_singleton.setLocalPlayer(server.loginUser(username, password).getUserCookie());
	}

	@Override
	public void register() {
		
		// TODO: register new user (which, if successful, also logs them in)
		String username = getLoginView().getRegisterUsername();
		String password = getLoginView().getRegisterPassword();
		String passrepeat = getLoginView().getRegisterPasswordRepeat();
		if(password.equals(password))
		{
			if(server.registerUser(username,password).getResponseCode()==HttpURLConnection.HTTP_OK)
			{
				System.out.println("i registered them");
				//getLoginView().closeModal();
				//loginAction.execute();
			}
			System.out.println("i failed to register");
		}
		
		//Get ID from cookie
		ModelFacade.facace_singleton.setLocalPlayer(server.loginUser(username, password).getUserCookie());
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		// TODO Auto-generated method stub
		
	}

}

