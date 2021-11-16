import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class InputHandler {

    public static String getString(){
        String start;
        start = "Welcome to Hangman";
        return start;
    }

    /**
     *
     * @param min takes menu inputs
     * @param max takes menu inputs
     * @return
     */
    public static int getInt(int min, int max){
        Scanner menuScanner = new Scanner(System.in);
        int x;
        try {
            x  = menuScanner.nextInt();
            if(x >= min && x <= max) return  x;
            else  return  -1 ;
        }catch (InputMismatchException e){
            return -1 ;
        }
    }

    /**
     *
     * Letting the user choose between quitting the game and staying in the menu
     */
    public static char getAlpha(char L) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Do you want to quit? Y/N");

        char userChoice = userInput.next().charAt(0);

        if (userChoice == 'y') {

            System.out.println("Have a nice day...");
            System.exit(0);
        } else if (userChoice == 'n'){
            System.out.println("You are back in the menu");
        } else {
            System.out.println("That is not a valid entry");
            getAlpha(L);
        }
        return L;
    }
}
