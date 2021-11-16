
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;


public class Game {

    private Player currentPlayer = null;
    private static ArrayList<Player> currentPlayers = new ArrayList<>();
    private int index;
    private char[] myAnswers ;
    private char[] wordToGuess = null;
    private boolean finished;
    private int lives;

    public Game(Player currentPlayer){
        this.currentPlayer = currentPlayer;
        System.out.println("Hi welcome to hangman " + currentPlayer.getName() + "!" + " (press # to return to menu)");
        char[] wordToGuess = getWordFromDictionary();
        this.myAnswers = new char[wordToGuess.length];
        this.finished = false;
        this.lives = 6 ;
        playGame(wordToGuess);
    }

    /**
     *
     * Starts game for Single Player
     */
    private void playGame(char [] wordToGuess){
        for(int i = 0; i < myAnswers.length; i++){
            myAnswers[i] = '?';
        }
        Scanner input = new Scanner(System.in);
        while(!finished) {
            String let = input.next();
            /* check for a valid input */
            while(let.length() != 1 || Character.isDigit(let.charAt(0))){
                System.out.println("Error Input - Try again");
                let = input.next();
            }
            //check if letter is in the word
            boolean found = false;
            for(int i = 0; i < wordToGuess.length; i++) {
                if(let.charAt(0) == wordToGuess[i]) {
                    myAnswers[i] = wordToGuess[i];
                    found = true;
                }
            }
            if(!found){
                lives--;
                System.out.println("Wrong letter");
            }
            if (let.charAt(0) == '#'){
                System.out.println("");
                finished = true;
            }
            boolean done = true;
            for (char myAnswer : myAnswers) {
                if (myAnswer == '?') {
                    System.out.print("_");
                    done = false;
                } else {
                    System.out.print(" " + myAnswer);
                }
            }
            System.out.println("\n" + "Lives left: " + lives);
            drawHangman(lives);
            checkGameResults(done);
        }
        System.out.print("The word was: ");
        System.out.print(wordToGuess);
    }

    /**
     * checkGameResult
     * @param done Checks if the player has won the game, if livs are 0, the player well lose.
     *
     */
    private void checkGameResults(boolean done) {

        if(done) {
            System.out.println("Congratulations! You are now back to the menu");
            System.out.println();
            finished = true;
            currentPlayer.setRounds(currentPlayer.getRounds()+ 1);
            currentPlayer.setWins(currentPlayer.getWins() + 1);
            currentPlayer.setTotalPoint(currentPlayer.getTotalPoint() + 5);
        }
        if(lives <= 0) {
            System.out.println("YOU ARE DEAD AND BANISHED BACK TO THE MENU! >:D");
            System.out.println();
            finished = true;
            currentPlayer.setRounds(currentPlayer.getRounds()+ 1);
            currentPlayer.setLosses(currentPlayer.getLosses() + 1);
        }
    }

    //Constructor for MultiPlayer Game
    public Game(ArrayList<Player> currentPlayers){
        System.out.println("Welcome to hangman");
        this.wordToGuess =  getWordFromDictionary();
        //this.wordToGuess = new char[] {'q', 'w', 'e', 'r', 't'};
        this.myAnswers = new char[wordToGuess.length];

        for(int i = 0; i < myAnswers.length; i++){
            myAnswers[i] = '?';
        }
        this.currentPlayers = currentPlayers;
        Collections.shuffle(currentPlayers);
        this.index = 0;
        this.finished = false;
        //Starta spelet
        playMultiplayerGame();
    }

    /**
     * Starts game for Multi Player
     */
    public void playMultiplayerGame(){

        while(!finished){
            Scanner input = new Scanner(System.in);

            //if the loop goes to the last player (for example player 4) the index becomes 0 and goes back to the first player

            if(index == currentPlayers.size()){
                index = 0 ;
            }
            this.currentPlayer = currentPlayers.get(index);
            System.out.println(currentPlayer.getName() + " Plays now, Good Luck!                       lives:" + currentPlayer.getLives());
            System.out.println();
            while(true){
                int lives = currentPlayer.getLives();
                String str = input.next();

                // check for a valid input

                while(str.length() != 1 || Character.isDigit(str.charAt(0))){
                    System.out.println("Error Input - Try again");
                    str = input.next();
                }
                //check if str is in the word

                boolean found = false;
                for(int i = 0; i < wordToGuess.length; i++) {
                    if(str.charAt(0) == wordToGuess[i]) {
                        myAnswers[i] = wordToGuess[i];
                        found = true;
                    }
                }
                boolean done = true;
                for (char myAnswer : myAnswers) {
                    if (myAnswer == '?') {
                        System.out.print(" _");
                        done = false;
                    } else {
                        System.out.print(" " + myAnswer);
                    }
                }

                if(!found){
                    lives--;
                    currentPlayer.setLives(lives);
                    System.out.println("              Wrong letter");
                    System.out.println("\n" + "Lives left: " + lives);
                    if(lives != 0) index++;
                    drawHangman(lives);
                    checkMultiplayerGameResults(done);
                    break;
                }
                System.out.println("\n" + "Lives left: " + lives);
                drawHangman(lives);
                checkMultiplayerGameResults(done);
                if(done) break ;
            }
        }
        System.out.println("The word to guess was " + wordToGuess);
    }

    /**
     *
     * @param done Checks if a player wins or lose in Multi Player
     */
    private void checkMultiplayerGameResults(boolean done) {

        finished = true;
        for( Player p : currentPlayers){
            if(p.getLives() > 0 ) finished = false;
        }

        if(done) {
            System.out.println("Congratulations "+ currentPlayer.getName()+"! You will now and get 5 points");
            System.out.println();

            currentPlayer.setRounds(currentPlayer.getRounds()+ 1);
            currentPlayer.setWins(currentPlayer.getWins() + 1);
            currentPlayer.setTotalPoint(currentPlayer.getTotalPoint() + 5);

            System.out.println("Starting a new Game ");
            this.wordToGuess = getWordFromDictionary();
            this.myAnswers = new char[wordToGuess.length];
            for(int i = 0; i < myAnswers.length; i++){
                myAnswers[i] = '?';
            }
            Collections.shuffle(currentPlayers);
            this.index = 0;
        }
        if(currentPlayer.getLives() <= 0) {
            System.out.println("YOU ARE DEAD AND BANISHED  >:D");
            System.out.println();
            currentPlayer.setRounds(currentPlayer.getRounds()+ 1);
            currentPlayer.setLosses(currentPlayer.getLosses() + 1);
            currentPlayer.setLives(6);
            currentPlayers.remove(currentPlayer);
        }
    }

    /**
     *
     * @return returns a word from the english2.txt file (this file is used as a dictionary)
     */
    private char[] getWordFromDictionary()  {

        System.out.print("Guess a letter: ");
        ArrayList<String> words = new ArrayList<>();
        try{

            File dictionary = new File("Hangman/src/english2.txt");

            Scanner textScanner = new Scanner(dictionary);

            while(textScanner.hasNextLine()) {
                words.add(textScanner.nextLine());
            }
        }
        catch (IOException e){
            System.out.println(e);
        }
        String hiddenText = words.get((int)(Math.random() * words.size()));
        return hiddenText.toCharArray();
    }

    private void drawHangman(int l) {
        if(l == 6) {
            System.out.println("|----------");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");

        }
        else if(l == 5) {
            System.out.println("|----------");
            System.out.println("|    O");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");

        }
        else if(l == 4) {
            System.out.println("|----------");
            System.out.println("|    O");
            System.out.println("|    |");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");

        }
        else if(l == 3) {
            System.out.println("|----------");
            System.out.println("|    O");
            System.out.println("|   -|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");

        }
        else if(l == 2) {
            System.out.println("|----------");
            System.out.println("|    O");
            System.out.println("|   -|-");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");

        }
        else if(l == 1) {
            System.out.println("|----------");
            System.out.println("|    O");
            System.out.println("|   -|-");
            System.out.println("|   /");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");

        }
        else{
            System.out.println("|----------");
            System.out.println("|    O");
            System.out.println("|   -|-");
            System.out.println("|   /|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");

        }
    }
}