import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static final char CLEAR = '–';
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
    * @return arr - contains number of rows in arr[0] and number of columns in arr[1]
    */
    public static int[] inputBoardSize(){
        System.out.println("Enter the board size");
        String boardSize = scanner.nextLine();
        String[]  str = boardSize.split("x");
        int [] arr =new int[2];
        arr[0] =  Integer.parseInt(str[0]);
        arr[1] =  Integer.parseInt(str[1]);
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
    public static void input_battleships_placement(char [][] game_board, int n, int m, String battleshipsString){

    }
    /*
    function that place the battleships on the board.
    the function checks if the direction horizontal or vertical and place is accordingly in a loop.
    @params: game board - 2d's arr; the game board
            bslen - the length of the battleships that is being placed
            x and y - the coordinates of the beginning of the battleship place.
            dir - the direction that the battleship will be placed - vertical or horizontal

     */
    public static void placeUserBattleships( char [][] game_board, int bsLen, int x, int y, int dir ){
        if(dir == 1){
            for(int i = 0; i < bsLen; i++){
                game_board[x][y + i] = ALIVE;
            }
        }
        if(dir == 0){
            for(int i = 0; i < bsLen; i++){
                game_board[x + i][y] = ALIVE;
            }
        }
    }
    /*
    this function prints the board.
    @param: n - number of rows
            m - number of column
            board- any kind of board
     */
    public static void printBoard(int n, int m, int[][] board){
        System.out.print("  ");
        for(int i = 0; i < m; i ++)
            System.out.println(i);
        for(int k = 0; k < n; k++){
            System.out.print(k + " " );
            for(int j = 0; j < m; j++){
                System.out.print(board[k][j]);
            }
        }
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



