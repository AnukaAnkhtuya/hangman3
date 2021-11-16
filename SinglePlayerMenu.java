import java.io.*;
import java.util.*;

public class SinglePlayerMenu {

    private Scanner in = new Scanner(System.in);
    private Player currentPlayer;
    private Database database;

    public SinglePlayerMenu(Database db) throws IOException {
        this.database = db;
        init();
    }

    public void init() throws IOException {
        System.out.println(InputHandler.getString());
        boolean running;
        running = true;
        show();
        while (running) {

        // Den skaper ett nytt spel, när man startar game och skickar med concurntPlayer med så att game klassen får veta vem spelaren är.

            switch (InputHandler.getInt(1, 5)) {
                case 1:
                    if (currentPlayer != null) {
                        new Game(currentPlayer);
                        database.writeToFile();
                    } else {
                        System.out.println("Please choose a player before starting a Game");
                    }
                    break;
                case 2:
                    choosePlayer();
                    break;
                case 3:
                    addNewPlayer(); // ska kunna säga vilken spelare som du lägger till
                    break;
                case 4:
                    running = false;
                    break;
                case 5:
                    InputHandler.getAlpha('L');
                    break;
                default:
                    System.out.println("incorrect input");
                    break;
            }
            if(running) show();
        }
    }


    public void show() {
        if(currentPlayer != null){
            System.out.println("                Player: " + currentPlayer.getName() );
            System.out.println("----------------------------------------------");
        }
        // detta ska visa menyn
        System.out.println();
        System.out.println("1. Start game!");
        System.out.println("2. Choose a Player");
        System.out.println("3. Create a new Player");
        System.out.println("4. Back to Main Menu");
        System.out.println("5. Quit");

    }
    // Skapade en ny metod för att välja karaktär

    public void choosePlayer() {

        //Skriver ut alla spelare

        System.out.println(database.toString());
        System.out.println("Alternatives: ");
        System.out.println("* Enter 'N' to add a new palyer or 'B' to go Back to menu");
        System.out.println("* Enter 'B' to go Back to menu");
        System.out.println("* Enter a number to Choose a player from the list");

        String input = "";
        try {
            input = in.next();
            int index = Integer.parseInt(input);

            if(index <= Database.allPlayers.size() - 1 && index >= 0){
                currentPlayer = Database.allPlayers.get(index);
            }else{
                System.out.println("WARNING !!! ");
                System.out.println("please enter a number within the range: ");
                System.out.println();
                //Kalla på metoden igen, MEN då kan man inte gå tillbaks till menyn tills man väljer en spelare
                choosePlayer();
            }
        }catch (NumberFormatException e){
            if(input.equalsIgnoreCase("n")){
                addNewPlayer();
            }
            else if(input.toLowerCase().equals("b")){
            }
            else{
                System.out.println("Please choose one of the given alternatives");
                choosePlayer();
            }
        }
    }

    /**
     *
     * @param
     */

    public void addNewPlayer() {
        System.out.println("Add new player!");
        System.out.print("> ");
        try {
            String newPlayerName = in.nextLine(); //man läser in input från användaren från console
            boolean success = database.addPlayer(newPlayerName);
            if(success){
                currentPlayer = database.getPlayerByName(newPlayerName);
                System.out.println("Welcome: " + currentPlayer.getName() + "," + " Enter a number from the menu");
                database.writeToFile();
            }else{
                System.out.println("Player already exists, choose another name ");
                addNewPlayer();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("IException");
        }

    }

}

