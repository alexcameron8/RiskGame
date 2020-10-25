package Attack;

/**
 * This is a Dice class.
 *
 * @author Alex Cameron
 */
public class Dice {
    private int value;
    private final int max = 6;

    /**
     * Creates a new dice object.
     */
    public Dice(){
        value = 1;
    }

    /**
     * Rolls a number between 1-6
     * @return Roll value of Dice
     */
    public int roll(){
        value = (int)(Math.random() * max + 1);
        return value;
    }

    /**
     * gets the value of the dice.
     * @return Value of last roll.
     */
    public int getValue(){
        return value;
    }
}
