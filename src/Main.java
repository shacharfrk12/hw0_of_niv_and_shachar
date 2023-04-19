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

    static final String ENTER_BATTLESHIPS = "Enter the battleships sizes"
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

    /*
    * the function creates an empty ("-") board in the fitting size
    * @param n - number of lines in array
    * @param m - number of rows in array
    *
    * @returns an empty board (array of size n*m)
    * */
    public static int[][] initializeBoard(int n, int m){
        int[][] board = new int[n][m];
        for(int i=0; i < n; i++){
            for(int j=0; j < m; i++){
                board[i][j] = CLEAR;
            }
        }
        return board;
    }

    /*
    * the function receives battleships amounts and sizes as string from user
    * the string format is "n1Xs1n2Xs1..."
    *
    * @returns the string received from user
    * */
    public static String inputBattleships(){
        System.out.println(ENTER_BATTLESHIPS);
        String battleships = scanner.nextLine();
        return battleships;
    }
    public static void input_battleships_placement(int[][] game_board, int n, int m, String battleshipsString){

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



