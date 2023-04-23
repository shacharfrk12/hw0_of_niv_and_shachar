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
    static final int MARGIN_POINT = 2;
    static final int MARGIN_BS = 3;
    static final int OVERLAP = 4;
    static final int ADJACENT = 5;
    static final int VALID = 0;

    static final String ENTER_BATTLESHIPS = "Enter the battleships sizes";
    static final String ENTER_BATTLESHIPS_PLACE = "Enter location and orientation for battleship of size ";
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
    public static char[][] initializeBoard(int n, int m){
        char[][] board = new char[n][m];
        for(int i=0; i < n; i++){
            for(int j=0; j < m; i++){
                board[i][j] = CLEAR;
            }
        }
        return board;
    }

    /*
     *
     *
     * */
    public static void resetBoard(char[][] board, int n, int m){
        for(int i=0; i < n; i++){
            for(int j=0; j < m; i++){
                board[i][j] = CLEAR;
            }
        }
    }

    /*
     * the function receives battleships amounts and sizes as string from user
     * the string format is "n1Xs1n2Xs1..."
     * and extracts the information to a more convenient data structure
     *
     * @returns the string received from user
     * @returns array of battleships, each object of the array is a vector of number of battleships
     * according to size
     * */
    public static int[][] inputBattleships(){
        int len;
        String[] nXs;
        System.out.println(ENTER_BATTLESHIPS);
        String battleships = scanner.nextLine(); //receives a string in format "n1Xs1 n2Xs2 ..."
        String[] splitBattleships = battleships.split("\\s+"); //splits to battleships sorted by length
        len = splitBattleships.length;
        int[][] battleshipsArr = new int[len][2];
        for(int i = 0; i < len ; i++){
            nXs = splitBattleships[i].split("X"); //splits to number of ships and length
            battleshipsArr[i][0] = Integer.parseInt(nXs[0]);
            battleshipsArr[i][1] = Integer.parseInt(nXs[1]);
        }
        return battleshipsArr;
    }
    /*
     *
     *
     *
     * */
    public static void manageInputBattleships(char[][] gameBoard, int n, int m){
        int[][] battleships = inputBattleships();
        while(!manageBattleshipsPlacement(gameBoard, n, m, battleships)){
            battleships = inputBattleships();
        }
    }


    /*
     * receives placement info: location(x,y) and direction(orientation) and arranges it in an array
     *
     * @param size: size of ship that needs to be placed;
     *
     * @return integer array that contains {x,y,dir}
     * */
    public static int[] inputBattleshipPlacement(int size){
        int[] splitInt = new int[3];
        System.out.println(ENTER_BATTLESHIPS_PLACE+size);
        String battleshipPlacementStr = scanner.nextLine(); //"x, y, orientation"
        String[] splitStr = battleshipPlacementStr.split(", ");// splits information
        //enters the info into an int array
        for(int i = 0; i<3; i++){
            splitInt[i] = Integer.parseInt(splitStr[i]);
        }
        return splitInt;
    }

    /*
     *
     *
     *
     * */
    public static boolean manageBattleshipsPlacement(char[][] gameBoard, int n, int m, int[][] battleships){
        int size, validVal;
        int[] placement;
        int len = battleships.length;
        for(int i = 0; i < len; i++){
            for(int j = 0; j < battleships[i][0]; j++){
                placement = inputBattleshipPlacement(battleships[i][0]);
                size = battleships[i][1];
                validVal = validBattleships(gameBoard, n, m, size, placement[0], placement[1], placement[2]);
                if(validVal==VALID){
                    placeUserBattleships(gameBoard, size, placement[0], placement[1], placement[2]);
                }
                else {
                    resetBoard(gameBoard, n, m);
                    return false;
                }
            }
        }
        return true;
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
        if(dir == 1 ){
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
    public static int validBattleships(char[][] gameBoard, int n, int m, int size, int x, int y, int dir){
        if (dir != 0 && dir != 1){
            return DIR;
        }
        if(x >= m || y >= n){
            return MARGIN_POINT;
        }
        if((dir == 0) && (x + size >= m) ){
            return MARGIN_BS;
        }
        if ((dir == 1) && (y+  size >=n) ){
            return MARGIN_BS;
        }
        for(int i = 0 ; i <= size; i ++){
            if(dir == 0){
                if(gameBoard[x + i][y] == ALIVE)
                    return OVERLAP;
            }
            if(dir == 1 && gameBoard[x][y + 1] == ALIVE){
                return OVERLAP;
            }
            for(int j = -1; j < 2; j++){
                for(int k = -1; k < 2; k++ ){
                    if(gameBoard[x + j][y + k] == ALIVE)
                        return ADJACENT;
                }
            }
        }
        return 0;
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