import java.io.*;
import java.util.ArrayList;

public class Player implements Comparable<Player> {

    protected int rounds;
    protected String name;
    protected int wins;
    protected int losses;
    protected int totalPoint;
    private int lives;

    //användas för att skapa en ny spelare
    public Player(String name) {
        this.name = name;
        this.rounds = 0;
        this.wins = 0;
        this.losses = 0;
        this.totalPoint = 0;

        this.lives = 6;

    }

    public Player(String name, int rounds, int wins, int losses, int totalPoint) {
        this.name = name;
        this.rounds = rounds;
        this.wins = wins;
        this.losses = losses;
        this.totalPoint = totalPoint;

        this.lives = 6;
    }

    public static void activatePlayer() {
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }


    @Override
    public int compareTo(Player p) {
        Integer ourPoints = this.getTotalPoint();
        Integer otherPoints = p.getTotalPoint();

        //this.getTotalPoint();  // int
        //Tar emot objekt. String
        // String. ---- char C
        // Integer ---- int
        return ourPoints.compareTo(otherPoints);
    }

    @Override
    public String toString() {

        int index = Database.allPlayers.indexOf(this);
        return /*name + ": " +
                " Rounds:" + rounds +
                " Wins:" + wins +
                " Losses:" + losses*/
                index + " - Name: " + this.name + ", Wins: " + this.wins + ", Games played: " + this.rounds +
                        ", Total points: " + this.totalPoint + " \n";
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

}

