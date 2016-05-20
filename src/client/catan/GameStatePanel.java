package client.catan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import client.base.IAction;
import client.model.ModelFacade;


@SuppressWarnings("serial")
public class GameStatePanel extends JPanel implements Observer
{
	private JButton button;
	
	public GameStatePanel()
	{
		this.setLayout(new FlowLayout());
		this.setBackground(Color.white);
		this.setOpaque(true);
		ModelFacade.facace_currentgame.addObserver(this);

		button = new JButton();
		
		Font font = button.getFont();
		Font newFont = font.deriveFont(font.getStyle(), 20);
		button.setFont(newFont);
		
		button.setPreferredSize(new Dimension(400, 50));
		
		this.add(button);
		
		updateGameState("Waiting for other Players", false);
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
				System.out.println("I pressed the button");
				action.execute();
			}
		};
		button.addActionListener(actionListener);
	}

	//I PRAY THIS DOESN"T DESTROY EVERYTHING
	@Override
	public void update(Observable o, Object arg)
	{

		updateGameState("Next turn",true);
	}
}

