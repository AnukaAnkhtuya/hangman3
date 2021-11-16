import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;


public class Database {

    public static ArrayList<Player> allPlayers = new ArrayList<Player>() ;
    protected File playerData;
    protected Scanner scan;
    private static ArrayList<Player> currentPlayers = new ArrayList<>();




    public Database() {
        playerData = new File("Hangman/src/PlayerData.txt");
        populateDatabase();
    }



    /**
     * Reads data from a Textfile and populates our arraylist of players respectively
     */
    public void populateDatabase() {
        try {
            scan = new Scanner(playerData);
            while (scan.hasNext()) {
                String playerName = scan.next();
                int playerRound = Integer.parseInt(scan.next());
                int playerWins = Integer.parseInt(scan.next());
                int playerLosses = Integer.parseInt(scan.next());
                int playerTotalPoint = Integer.parseInt(scan.next());
                Player p = new Player(playerName, playerRound, playerWins, playerLosses, playerTotalPoint);
                allPlayers.add(p);

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("catch");
        }
        scan.close();
    }

    public void writeToFile(){
        try {
            scan = new Scanner(playerData);
            PrintWriter writer = new PrintWriter(playerData);
            for(Player player: allPlayers){
                String name = player.getName();
                int rounds = player.getRounds();
                int wins = player.getWins();
                int losses = player.getLosses();
                int totalPoint = player.getTotalPoint();
                writer.println(name + " "  + rounds+ " " + wins + " " + losses + " " + totalPoint);
            }
            writer.close();
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        }
    }

    public boolean addPlayer(String playerName){
        boolean alreadyExists = false;
        for(Player player : allPlayers){
            if(player.getName().equals(playerName)){
                alreadyExists = true;
                break;
            }
        }
        if(alreadyExists == true)
            return false;
        else
            return allPlayers.add(new Player(playerName));
    }

    /**
     *
     * @param playerName Name of the player to get from allPlayersList
     * @return null if player dont exits OR return the player object from the list
     */
    public Player getPlayerByName(String playerName){
        for(int i = 0 ; i < allPlayers.size() ; i++){
            Player player = allPlayers.get(i);
            if(player.getName().equals(playerName)){
                return allPlayers.get(i);
            }
        }
        return  null;
    }

    /**
     *
     * @return Ranks the top 5 players by their total points
     */
    public String getHighScore(){

        System.out.println("Highscore");
        System.out.println("---------------");
        String highscore = "";
        Collections.sort(allPlayers, Collections.reverseOrder());

        for(int i = 0 ; i < allPlayers.size() && i < 5; i++){
            String playerName = allPlayers.get(i).getName();
            int playerTotalPoints = allPlayers.get(i).getTotalPoint();
            highscore += "-"+(i+1)  + " " +playerName +": "+ playerTotalPoints + "\n";
        }

        try {
            Scanner sc =  new Scanner("Hangman/src/Highscore.txt");
            PrintWriter writer = new PrintWriter("Hangman/src/Highscore.txt");
            writer.println(highscore);
            sc.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return highscore ;
    }
    @Override
    public String toString() {
        return "" + allPlayers ;
    }
}
