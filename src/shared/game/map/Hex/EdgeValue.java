package shared.game.map.Hex;

import shared.game.PlayerIndex;
import shared.locations.EdgeLocation;

public class EdgeValue
{
	PlayerIndex owner;
	EdgeLocation location;
	EdgeValue(PlayerIndex owner, EdgeLocation location)
	{
		this.owner=owner;
		this.location=location;
	}
	
	

}
