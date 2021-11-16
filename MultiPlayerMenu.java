import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MultiPlayerMenu {

    private Scanner in = new Scanner(System.in);
    private ArrayList<Player> currentPlayers = new ArrayList<Player>();
    private Database database;


    public MultiPlayerMenu(Database database1) throws IOException {
        this.database = database1;
        init();
    }

    public void init() throws IOException {
        System.out.println(InputHandler.getString());
        boolean running;
        running = true;
        show();
        while (running) {
//       // den skaper ett nytt spel, när man startar game och skickar med currentplayer med så att game klassen får veta vem spelaren är.

            switch (InputHandler.getInt(1, 5)) {
                case 1:
                    if(currentPlayers.size() >= 2 && currentPlayers.size() <= 4) {
                        new Game(currentPlayers);
                        database.writeToFile();
                    }
                    else {
                        System.out.println("Please choose 2 to 4 players before starting a Game");
                        //Behövs inte kanske. Låt användaren själv välja 2an. Det kanske blir tydligare
                        //choosePlayers();
                    }
                    break;
                case 2:
                    currentPlayers.clear();
                    choosePlayers();
                    break;
                case 3:
                    addNewPlayer();
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
        if(currentPlayers.size() >= 2 && currentPlayers.size() <= 4){
            System.out.println("Players: ");
            for(Player currentPlayer : currentPlayers){
                System.out.print(" " + currentPlayer.getName());
            }
        }
        System.out.println();
        System.out.println("1. Start game!");
        System.out.println("2. Choose a Player");
        System.out.println("3. Create a new Player ");
        System.out.println("4. Back to Main Menu");
        System.out.println("5. Quit");
    }

    public void choosePlayers() {
        System.out.println("Choose the number of players (between 2 and 4):");

        int numberOfPlayers = InputHandler.getInt(2, 4);

        if(numberOfPlayers == -1){
            System.out.println("Wrong input ...");
            choosePlayers();
        }

        for( int i = 0;  i < numberOfPlayers ; i++){
            System.out.println(database.toString());
            System.out.println("Alternatives: ");
            System.out.println("* Enter 'N' to add a new player or 'B' to go Back to menu");
            System.out.println("* Enter 'B' to go Back to menu");
            System.out.println("* Enter a number to Choose player number {{" + (i + 1)  +"}} from the list");

            String input = "";

            try {
                input = in.next();
                int index = Integer.parseInt(input);

                if(index <= Database.allPlayers.size() - 1 && index >= 0){
                    currentPlayers.add(Database.allPlayers.get(index));
                }else{
                    System.out.println("WARNING !!! ");
                    System.out.println("please enter a number within the range: ");
                    System.out.println();
                    i--;
                }
            }catch (NumberFormatException e){
                if(input.equals("n")){
                    Player newPlayer = addNewPlayer();
                    if(newPlayer != null)  currentPlayers.add(Database.allPlayers.get(Database.allPlayers.size() - 1));
                    else i--;
                }
                else if(input.equals("b")){
                    currentPlayers.clear();
                    break;
                }
                else{
                    System.out.println("Please choose one of the given alternatives");
                    i--;
                }
            }
        }
    }

    public Player addNewPlayer() {
        System.out.println("Add new player!");
        System.out.print("> ");
        try {
            Scanner keyboard = new Scanner(System.in);

            String newPlayerName = keyboard.nextLine(); //man läser in input från användaren från console.

            boolean success = database.addPlayer(newPlayerName);
            if(success){
                Player results = database.getPlayerByName(newPlayerName);
                System.out.println("player '" + newPlayerName + "' was successfully added to the database");
                database.writeToFile();
                return results;
            }else{
                System.out.println("Player already exists, choose another name ");
                addNewPlayer();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("IException");
        }
        return null;
    }

}
