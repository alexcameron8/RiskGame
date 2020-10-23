package Main;

public class Risk {
    static GameState state;

    private void printMenu(){
        System.out.println("Welcome to Main.Risk: Global Domination!");
        System.out.println("To get started, please select and option:");
        System.out.println("1. Start new game.");
        System.out.println("2. Quit");
    }

    public GameState getState(){
        return state;
    }

    public static void main(String[] args) {
        state = GameState.MAIN_MENU;

        while(state != GameState.QUIT){

        }

    }
}
