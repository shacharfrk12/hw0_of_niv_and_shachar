import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static final char CLEAR = '-';
    static final char HIT = 'v';
    static final char MISS = 'x';
    static final char ALIVE = '#';
    static final char ATTACKED = 'x';
    static final int DIR = 1;
    static final int MARGIN = 2;
    static final int OVERLAP = 3;
    static final int ADJACENT = 4;
    public static Scanner scanner;
    public static Random rnd;

    /*
    * the function receive the board size from the user,
    * both row and col sizes
    * @return arr - contains rownum in arr[0] and colnum in arr[1]
    *  */
    public static int[] inputBoardSize(){
        System.out.println("Enter the board size");
        String boardSize = scanner.nextLine();
        int rowNum = (int) boardSize.charAt(0) - 48;//converting into int
        int colNum = (int) boardSize.charAt(2) - 48;//same
        int [] arr = {rowNum, colNum};
        return arr;
    }
    public static void initializeBoard(){

    }
    public static void inputBattleships(){

    }
    public static void placeUserBattleships(){

    }
    public static void printBoard(){

    }
    public static void placeComputerBattleships(){

    }
    public static int validBattleships(){

    }
    public static void printValidation(){

    }
    public static void battleshipGame() {
        // TODO: Add your code here (and add more methods).
    }


    public static void main(String[] args) throws IOException {
        String path = args[0];
        scanner = new Scanner(new File(path));
        int numberOfGames = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Total of " + numberOfGames + " games.");

        for (int i = 1; i <= numberOfGames; i++) {
            scanner.nextLine();
            int seed = scanner.nextInt();
            rnd = new Random(seed);
            scanner.nextLine();
            System.out.println("Game number " + i + " starts.");
            battleshipGame();
            System.out.println("Game number " + i + " is over.");
            System.out.println("------------------------------------------------------------");
        }
        System.out.println("All games are over.");
    }
}



