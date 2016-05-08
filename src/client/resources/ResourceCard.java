package client.resources;

public abstract class ResourceCard extends shared.game.Card
{
	public ResourceCard(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	public abstract boolean canEarnResourceCard();
	public abstract boolean canSpendResources();
}
