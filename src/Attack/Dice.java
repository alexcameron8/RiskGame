package Attack;

/**
 * This is a Dice class.
 */
public class Dice {
    private int value;
    private final int max = 6;
    public Dice(){
        value = 1;
    }

    /**
     * Rolls a number between 1-6
     * @return
     */
    public int roll(){
        value = (int)(Math.random() * max + 1);
        return value;
    }

    /**
     * gets the value of the dice.
     * @return
     */
    public int getValue(){
        return value;
    }
}
