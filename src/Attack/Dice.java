package Attack;

public class Dice {
    private int value;
    private final int max = 6;
    public Dice(){
        value = 1;
    }
    public int roll(){
        value = (int)(Math.random() * max + 1);
        return value;
    }
    public int getValue(){
        return value;
    }

    public static void main(String[] args) {
        Dice die1 = new Dice();
        Dice die2 = new Dice();
        die1.roll();
        die2.roll();
        System.out.println(die1.getValue());
        System.out.println(die2.getValue());
    }
}
