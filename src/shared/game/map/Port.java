package shared.game.map;

//import com.sun.xml.internal.ws.wsdl.writer.document.PortType;

import shared.definitions.PortType;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;

/**
 * @author Alex
 * Port class: represents ports/harbors.
 */
public class Port 
{
	/**
	 * Location of the port - which hex it is on
	 */
	private HexLocation location = new HexLocation(0, 0);

	/**
	 * The player who owns this port
	 */
	private Index owner = new Index(0);
	/**
	 * type: Enum type defined in package "definitions".
	 * Represents whether the port is for wood, sheep,
	 * brick, sheep, wheat, ore, or three. 
	 */

	private PortType type = PortType.ORE;

	/**
	 * which direction (i.e., N S E W etc.) the port is in on the particular hex
	 */
	private EdgeDirection direction = EdgeDirection.North;

	/**
	 * ratio: which ratio of trade this is (should only be 1, 2, or 3)
	 */
	private int ratio = -1;


	/**
	 * Another constructor for port.
	 */
	public Port(HexLocation location, EdgeDirection direction, int ratio, PortType type)
	{
		this.type=type;
		this.location = location;
		this.direction = direction;
		this.ratio = ratio;
	}

	/**
	 * Getters and setters:
	 */

	public HexLocation getLocation()
	{
		return location;
	}

	public void setLocation(HexLocation location)
	{
		this.location = location;
	}

	public Index getOwner()
	{
		return owner;
	}

	public void setOwner(Index owner)
	{
		this.owner = owner;
	}

	public PortType getType()
	{
		return type;
	}

	public void setType(PortType type)
	{
		this.type = type;
	}

	public EdgeDirection getDirection()
	{
		return direction;
	}

	public void setDirection(EdgeDirection direction)
	{
		this.direction = direction;
	}

	public int getRatio()
	{
		return ratio;
	}

	public void setRatio(int ratio)
	{
		this.ratio = ratio;
	}
}

