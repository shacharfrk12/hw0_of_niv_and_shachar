import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static final char CLEAR = '–';
    static final char HIT = 'V';
    static final char MISS = 'X';
    static final char ALIVE = '#';
    static final char ATTACKED = 'X';
    static final int DIR = 1;
    static final int MARGIN_POINT = 2;
    static final int MARGIN_BS = 3;
    static final int OVERLAP = 4;
    static final int ADJACENT = 5;
    static final int VALID = 0;
    static final String[] PLACEMENT_ERROR = {"", "Illegal orientation, try again!", "Illegal tile, try again!",
            "Battleship exceeds the boundaries of the board, try again!","Battleship overlaps another battleship, try again!",
            "Adjacent battleship detected, try again!"};
    public static Scanner scanner;
    public static Random rnd;

    //general functions

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
            for(int j=0; j < m; j++){
                board[i][j] = CLEAR;
            }
        }
        return board;
    }

    /*
    this function prints the board.
    @param: n - number of rows
    @param: m - number of column
    @param: board - any kind of board
     */
    public static void printBoard(int n, int m, char[][] board){
        System.out.print("  ");
        for(int i = 0; i < m; i ++)
            System.out.print(i + " ");
        for(int k = 0; k < n; k++){
            System.out.println("");
            System.out.print(k + " " );
            for(int j = 0; j < m; j++){
                System.out.print(board[k][j] + " ");
            }

        }
        System.out.println("\n");
    }

    //setting up the game - before competing functions

    //general setup functions

    /*
     * manages battleship placement for user\computer, according to isComputer parameter
     * @param: gameBoard - game board of user\computer
     * @param: n - number of rows
     * @param: m - number of column
     * @param: battleships - an array of battleships, each object in the array is a 2d array that contains
     * the information: {number of ships in this size, size of ships}
     * */
    public static void manageBattleshipsPlacement(char[][] gameBoard, int n, int m, int[][] battleships,
                                                  boolean isComputer){

        int size, validVal;
        int[] placement;
        int len = battleships.length;
        //goes over the battleships array
        for(int i = 0; i < len; i++){
            // the second value of the array battleships[i] marks the size of ships
            size = battleships[i][1];
            //for each size of ship (each object of the array) we ask for the user/computer to place n1 ships
            //(placing all ships of size "size")
            for(int j = 0; j < battleships[i][0]; j++){
                //placing battleship j of the same size until valid
                if(!isComputer)
                    System.out.println("Enter location and orientation for battleship of size "+size);
                do{
                    //checks if user\computer is placing, calls the placement function accordingly
                    if(isComputer){
                        placement = randomizeBattleshipPlacement(n, m); //randomization for battleship j
                    }
                    else {
                        placement = inputBattleshipPlacement(); //input for battleship j
                    }
                    //validation fo placement
                    validVal = validBattleships(gameBoard, n, m, size, placement[0], placement[1], placement[2]);
                    //Error output - only shows for user placings
                    if(!isComputer)
                        printValidation(validVal);
                }
                while(validVal!=VALID);

                //after receiving a valid output we place the battleship on the board
                placeBattleships(gameBoard, size, placement[0], placement[1], placement[2]);

                if(!isComputer){
                    System.out.println("Your current game board:");
                    printBoard(n, m, gameBoard);
                }
            }
        }
    }

    /*
    function that place the battleships on the board.
    the function checks if the direction horizontal or vertical and place is accordingly in a loop.
    @params: game board - 2d's arr; the game board
            bslen - the length of the battleships that is being placed
            x and y - the coordinates of the beginning of the battleship place.
            dir - the direction that the battleship will be placed - vertical or horizontal

     */
    public static void placeBattleships( char [][] gameBoard, int bsLen, int x, int y, int dir ){
        if(dir == 1 ){
            for(int i = 0; i < bsLen; i++){
                gameBoard[x+i][y] = ALIVE;
            }
        }
        if(dir == 0){
            for(int i = 0; i < bsLen; i++){
                gameBoard[x][y+i] = ALIVE;
            }
        }
    }

    //user input functions

    /*
     * the function receive the board size from the user,
     * both row and col sizes
     * @return arr - contains number of rows in arr[0] and number of columns in arr[1]
     */
    public static int[] inputBoardSize(){
        System.out.println("Enter the board size");
        String boardSize = scanner.nextLine();
        String[]  str = boardSize.split("X");
        int [] arr = new int[2];
        arr[0] =  Integer.parseInt(str[0]);
        arr[1] =  Integer.parseInt(str[1]);
        return arr;
    }

    /*
     * manage battleships placement aat the beginning of the game
     * @param: userBoard - the game board of the user
     * @param: compBoard - the game board of the computer
     * @param: n - number of rows
     * @param: m - number of column
     * */
    public static int manageInputBattleships(char[][] userBoard, char[][] compBoard, int n, int m){
        // receives battleships number and length input from user
        int[][] battleships = inputBattleships();

        //printing the user game board
        System.out.println("Your current game board:");
        printBoard(n, m, userBoard);

        // manages battleships placement input , output and updates the board accordingly;
        manageBattleshipsPlacement(userBoard, n, m, battleships, false);

        //printing the user game board
        //System.out.println("Your current game board:");
        //printBoard(n, m, userBoard);

        //manages battleships placement for computer and updates the board accordingly;
        manageBattleshipsPlacement(compBoard, n, m, battleships, true);
        int count = 0;
        for(int i = 0; i < battleships.length; i++){
            count += battleships[i][0];
        }
        return count;
    }

    /*
     * the function receives battleships amounts and sizes as string from user
     * the string format is "n1Xs1n2Xs1..."
     * and extracts the information to a more convenient data structure
     *
     * @returns array of battleships, each object of the array is a vector of number of battleships
     * according to size
     * */
    public static int[][] inputBattleships(){
        int len;
        String[] nXs;
        System.out.println("Enter the battleships sizes");
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
     * receives placement info for specific ship:
     * location(x,y) and direction(orientation)
     * and arranges it in an array
     *
     * @param size: size of ship that needs to be placed;
     *
     * @return integer array that contains {x,y,dir}
     * */
    public static int[] inputBattleshipPlacement(){
        int[] splitInt = new int[3];
        String battleshipPlacementStr = scanner.nextLine(); //"x, y, orientation"
        String[] splitStr = battleshipPlacementStr.split(", ");// splits information
        //enters the info into an int array
        for(int i = 0; i<3; i++){
            splitInt[i] = Integer.parseInt(splitStr[i]);
        }
        return splitInt;
    }

    //computer set up functions

    /*
    * selects random x, y, orientation values for one computer ship
    * @param: n - number of rows
    * @param: m - number of columns
    * @return info array of size 3, contains the randomized information: {x, y, dir}
    * */
    public static int[] randomizeBattleshipPlacement(int n, int m){
        int x = rnd.nextInt(n);
        int y = rnd.nextInt(m);
        //choosing between 0 and 1 orientation
        int dir = rnd.nextInt(2);
        int[] info = {x, y, dir};
        return info;
    }

    // game functions

    /*
     *this function display the player turn: the choosing of the tle to attack,
     * the tile validation, updating the needed boards and counting the number
     * of battleships that has left on the computer's board.
     * @param n: the number of rows
     * @param m: the number of columns
     * @param guessingBoard: the board that contains the player's guessing
     * @param computerBoard: the computer's game board
     * @param r : the number of battleships left
     *
     * @return integer that contains updated r
     * */
    public static int playerTurn(int n, int m, char[][] guessingBoard, char[][] computerBoard, int r,
                                 boolean isComputer){

        int[] guess;
        if(isComputer){
            guess = computerGuess(n, m, guessingBoard);
        }
        else{
            guess = userGuess(n, m, guessingBoard);
        }
        int x = guess[0], y = guess[1];

        //this block checks if it is a miss and mark it
        if(computerBoard[x][y] == CLEAR){
            System.out.println("That is a miss!");
            guessingBoard[x][y] = MISS;
        }

        //this block checks if it is a hit, mark it and update the battleships left
        if(computerBoard[x][y] == ALIVE){
            System.out.println("That is a hit!");
            computerBoard[x][y] = ATTACKED;
            guessingBoard[x][y] = HIT;
            if (battleshipDrowned(computerBoard, n, m, x, y)){
                if(isComputer){
                    System.out.println("Your battleship has been drowned, you have left " + (r - 1) + " more battleships!");
                }
                else {
                    System.out.println("The computer's battleship has been drowned, " + (r - 1) + " more battleships to go!");
                }
                return r - 1;
            }
        }
        return r;
    }

    /*
    * receives input for user guess in one turn
    *
    * @param: n - number of rows
    * @param: m - number of columns
    * @param guessingBoard - the user's guessing board
    *
    * @return an array of len 2 that contains the x,y coordinates of guessed by the user
    * */
    public static int[] userGuess(int n, int m, char[][] guessingBoard){
        System.out.println("Enter a tile to attack");
        int x, y;
        boolean valid;
        do
        {
            valid = true;
            String tta = scanner.nextLine();
            String[] str = tta.split(", ");
            x = Integer.parseInt(str[0]);
            y = Integer.parseInt(str[1]);
            //this block checks if the player enter a valid tile
            if(x >=n || y >= m || x < 0 || y < 0) {
                System.out.println("Illegal tile, try again!");
                valid = false;
            }
            else if(guessingBoard[x][y] == MISS || guessingBoard[x][y] == HIT) {
                System.out.println("Tile already attacked, try again!");
                valid = false;
            }
        } while (!valid);

        int[] guess = {x, y};
        return guess;
    }

    /*
     * generates random computer guess of one turn
     *
     * @param: n - number of rows
     * @param: m - number of columns
     * @param guessingBoard - the computer's guessing board
     *
     * @return an array of len 2 that contains the x,y coordinates of computer random guess
     * */
    public static int[] computerGuess(int n, int m, char[][] guessingBoard){
        int[] guess = {0, 0};
        do{
            guess[0] = rnd.nextInt(n);
            guess[1] = rnd.nextInt(m);
        }
        while(guessingBoard[guess[0]][guess[1]]==MISS || guessingBoard[guess[0]][guess[1]]==HIT);

        System.out.println("The computer attacked (" + guess[0] + ", " + guess[1] + ")");

        return guess;
    }

    //validation functions

    /*
     *this function validates the placement of the battleships
     * @param n: the number of rows
     * @param m: the number of columns
     * @param gameBoard: the game board of the player
     * @param size: size of the current battleship
     * @param x,y,dir: the wanted tile and the direction
     * (weather its vertical or horizontal )
     *
     * @return integer that testifies on the type of the
     * validation mistake or zero if its valid
     * */
    public static int validBattleships(char[][] gameBoard, int n, int m, int size, int x, int y, int dir){
        if (dir != 0 && dir != 1){
            return DIR;
        }
        if(x >= n || y >= m || x<0 || y<0){
            return MARGIN_POINT;
        }
        if((dir == 0) && (y + size > m)){
            return MARGIN_BS;
        }
        if ((dir == 1) && (x + size > n)){
            return MARGIN_BS;
        }
        for(int i = 0 ; i < size; i++) {
            if (dir == 0) {
                if (gameBoard[x][y + i] == ALIVE)
                    return OVERLAP;
            }
            if (dir == 1 && gameBoard[x + i][y] == ALIVE) {
                return OVERLAP;
            }
        }
        for(int i = 0 ; i < size; i++) {

            for(int j = -1; (j < 2); j++){
                for(int k = -1; (k < 2); k++){
                    if((x+j<n) && (x+j>=0) && (y+k<m) && (y+k>=0) && gameBoard[x + j][y + k] == ALIVE)
                        return ADJACENT;
                }
            }

            if(dir==0)
                y++;
            else
                x++;
        }
        return 0;
    }

    /*
     * printing an error message according to validation value
     * @param: validVal - int, a number that represents the kind of placement error committed
     */
    public static void printValidation(int validVal){
        if(validVal!=VALID){
            System.out.println(PLACEMENT_ERROR[validVal]);
        }
    }

    // game progress testers

    /*
    * checks if the currently hit battleship was drowned by that hit (was hit in all its spots)
    *
    * @param: gameBoard - the player's board that was attacked
    * @param: n - number of rows
    * @param: m - number of columns
    * @param: hitX - the row index of the hit
    * @param: hitY - the column index of the hit
    *
    * @return true if the ship was drowned, false otherwise
    * */
    public static boolean battleshipDrowned(char[][] gameBoard, int n, int m, int hitX, int hitY){
        int posX, posY;
        int[][] jumps= {{1, -1, 0, 0}, {0, 0, 1, -1}};
        //going over the 4 possible directions that the ship could continue at
        for(int i = 0; i<4;i++) {
            posX = hitX;
            posY = hitY;
            //stopping at the first square that isn't 'V' (hit)
            while (posX < n && posY < m && posX>=0 && posY>=0 && gameBoard[posX][posY] == ATTACKED) {
                posX += jumps[0][i];
                posY += jumps[1][i];
            }
            //if the first square in that direction is an alive square the ship has not drowned
            if (posX < n && posY < m && posX>=0 && posY>=0 && gameBoard[posX][posY] == ALIVE) {
                return false;
            }
        }
        //if on all sides the first non hit is a miss/bound/clear, the ship has drowned
        return true;
    }

    /*
    * checks if the game is over (one of the players won) and prints endgame output
    *
    * @param r1 - number of the users ships that have not been sunk yet
    * @param r2 - number of the computers ships that have not been sunk yet
    *
    * @return true if one of the players won (user/computer), otherwise returns false
    * */
    public static boolean isWin(int userR, int computerR){
        if(computerR == 0){
            System.out.println("You won the game!");
            return true;
        }
        if(userR==0){
            System.out.println("You lost ):");
            return true;
        }
        return false;
    }

    //total game manager function

    /*
    * manages one game of battleships between user and computer
    *
    * */
    public static void battleshipGame() {
        // TODO: Add your code here (and add more methods).
        int[] boardSize = inputBoardSize();
        int n = boardSize[0], m = boardSize[1];
        int userR, computerR;
        boolean win = false;
        char[][] userGameBoard = initializeBoard(n, m);
        char[][] computerGameBoard = initializeBoard(n, m);
        char[][] userGuessBoard = initializeBoard(n, m);
        char[][] computerGuessBoard = initializeBoard(n, m);


        userR = manageInputBattleships(userGameBoard, computerGameBoard, n, m);
        computerR = userR;
        while(!win){
            //1. print player guessing board ("Your current guessing board:")
            System.out.println("Your current guessing board:");
            printBoard(n, m, userGuessBoard);
            //2.player turn (returns r)
            //returns new computerR - adds 1 according to weather or the player sunk a computer ship
            computerR = playerTurn(n, m, userGuessBoard, computerGameBoard, computerR, false);
            win = isWin(userR, computerR);
            if(win)
                break;
            //3.computer turn (returns r)
            userR = playerTurn(n, m, computerGuessBoard, userGameBoard, userR, true);
            //4. print player game board ("Your current game board")
            System.out.println("Your current game board:");
            printBoard(n, m, userGameBoard);
            //5. checks win
            win = isWin(userR, computerR);
        }

    }

    //main
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