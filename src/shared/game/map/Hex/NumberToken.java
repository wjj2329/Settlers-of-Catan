package shared.game.map.Hex;

/**
 * Created by Alex on 5/8/16.
 * Class to represent a number token. These are placed on each individual hex (except for NumberHex)
 * and they give it a value.
 */
public class NumberToken
{
    /**
     * The value of the token
     */
    private int value = -1;

    /**
     * Constructor for number token
     * @param value: value of the number token
     *             @pre: value is greater than 0
     */
    public NumberToken(int value)
    {
        this.value = value;
    }

    /**
     * Getters and setters:
     */
    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }
}
