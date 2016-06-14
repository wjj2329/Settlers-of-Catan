package client.catan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import client.State.State;
import client.base.IAction;
import client.login.LoginController;
import client.model.ModelFacade;
import org.json.JSONException;
import org.json.JSONObject;
import shared.game.map.Index;
import shared.game.player.Player;


@SuppressWarnings("serial")
public class GameStatePanel extends JPanel implements Observer
{
	private JButton button;
	
	public GameStatePanel()
	{
		this.setLayout(new FlowLayout());
		this.setBackground(Color.white);
		this.setOpaque(true);
		ModelFacade.facadeCurrentGame.addObserver(this);

		button = new JButton();
		
		Font font = button.getFont();
		Font newFont = font.deriveFont(font.getStyle(), 20);
		button.setFont(newFont);
		
		button.setPreferredSize(new Dimension(400, 50));
		
		this.add(button);
		
		updateGameState("Not Your Turn", false);
	}
	
	public void updateGameState(String stateMessage, boolean enable)
	{
		button.setText(stateMessage);
		button.setEnabled(enable);
	}
	
	public void setButtonAction(final IAction action)
	{
		ActionListener[] listeners = button.getActionListeners();
		for(ActionListener listener : listeners) {
			button.removeActionListener(listener);
		}
		
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//System.out.println("I pressed the button");
				action.execute();
			}
		};
		button.addActionListener(actionListener);
	}

	//I PRAY THIS DOESN"T DESTROY EVERYTHING
	@Override
	public void update(Observable o, Object arg)
	{
		Index currentTurn = ModelFacade.facadeCurrentGame.currentgame.getModel().getTurntracker().getCurrentTurn();
		Index playerWhoseTurnItIs = null;
		for (Player p : ModelFacade.facadeCurrentGame.currentgame.getMyplayers().values())
		{
			if (p.getPlayerIndex().equals(currentTurn))
			{
				playerWhoseTurnItIs = p.getPlayerID();
			}
		}
		if (playerWhoseTurnItIs == null)
		{
			return;
		}
		final Player player = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(playerWhoseTurnItIs);
		System.out.println("I COME AND CHEKC THE GAME STATE PANNEL "+ModelFacade.facadeCurrentGame.currentgame.getCurrentState());
		if(player==null)
		{
			System.out.println("SADLY THE PLAYER IS NULL");
		}
		if(player!=null && ModelFacade.facadeCurrentGame.currentgame.getCurrentState() == State.GamePlayingState)
		{
			System.out.println("I COME HERE AND MAKE THE BUTTON GO GOOD");
			if (ModelFacade.facadeCurrentGame.getLocalPlayer().getName().equals(player.getName()))
			{
				System.out.println("I SET THE STATUS TO BE GOOD TO GO");

				updateGameState("End Turn", true);
				setButtonAction(new IAction()
				{
					@Override
					public void execute()
					{
						 ModelFacade.facadeCurrentGame.getServer().finishTurn("finishTurn",
								player.getPlayerIndex().getNumber());
						updateGameState("Not Your Turn", false);
					}
				});
			}
		}

	}
}

