import java.io.IOException;

/**
 * Newton
 *
 * HangMan 2.0
 *
 * Kurs:
 * Objektorienterad programmering 1
 * Klass: Systemutvecklare Java 3 - Stockholm (SYSJS 3)
 * Termin och år: HT 2021
 * Författare: Henning, Sahra, Ankhtuya, Ahmed, Lahoud, Niklas.
 * Grupp Blaze/Lissabon
 * Lärare: Bill
 */
public class MainMenu {
        public static void main(String[] args) throws IOException, ClassNotFoundException {
            show();
            boolean running;
            running = true;

            while (running){
                switch (InputHandler.getInt(1, 5)) {
                    case 1:
                        new SinglePlayerMenu(new Database());
                        break;
                    case 2:
                        new MultiPlayerMenu(new Database());
                        break;
                    case 3:
                        System.out.println(new Database().getHighScore());
                        break;
                    case 4:
                        InputHandler.getAlpha('L');
                        break;
                    case 5:
                        System.out.println("No! I will not save or load the game!");
                        break;
                    default:
                        System.out.println("incorrect input");
                        break;
                }
                show();
            }
        }


    public static void show() {
        System.out.println();
        System.out.println("1. Single-Player");
        System.out.println("2. Multi-Player");
        System.out.println("3. Highscore list");
        System.out.println("4. Quit");
        System.out.println("5. Save and Loaded");

    }
}







