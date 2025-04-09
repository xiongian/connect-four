/* * * * * * * * * * * * * * * * * * * * * * * * * * 
* The ConnectFour class.                           *
*                                                  *   
* Ian Xiong                                        *
*                                                  *
* ICS 3U1                                          *
*                                                  *   
* Ms. Lam                                          *      
*                                                  *
* Last updated: June 12, 2023 (fixed save/load)    *
*                                                  *
* This class represents a Connect Four (TM)        *
* game, which allows two players to drop           *
* checkers into a grid until one achieves          *
* four checkers in a straight line.                *
*                                                  *
* * * * * * * * * * * * * * * * * * * * * * * * * */

//imports
import java.io.*;

public class ConnectFour {

   //import guis
   ConnectFourGUI gui;
   
   //Global constants
   final int EMPTY = 0;
   final int NUMPLAYER = 2;
   final int NUMROW = 6;
   final int NUMCOL = 7;
   final int WINCOUNT = 4;
   final String GAMEFILEFOLDER = "gamefiles";
   
   //Global variables
   int board[][];
   int curPlayer; 
   
   /*
   Initializes board to be empty
   */
   public void resetBoard () {
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            board[i][j] = EMPTY;
         }
      } 
      curPlayer = 1;
      gui.setNextPlayer(curPlayer);     
   }
   
   /*
   Parameter: Column number
   Returns: The bottommost empty slot in that column
   */
   public int locateEmptySlot (int colNum) {
      for (int i = NUMROW - 1; i >= 0; i--) {
         if (board[i][colNum] == EMPTY) {
            return i;
         }   
      }
      return -1;      
   }   
   /*
   Returns: true/false based on if the board is full
   */
   public boolean boardFull () {
      for (int i = 0; i < NUMCOL; i++) {
         if (board[0][i] == EMPTY) {
            return false;
         }   
      }
      return true;
   } 
   
   /*
   Parameter: Column #, row #
   Returns: the integer of vertically consecutive pieces including that slot
   */
   public int verticalConnect (int colNum, int rowNum) {
        
      int consecutiveCount = 1;     
      
      //counts consecutive going down
      int downRow = rowNum + 1;
      while (downRow < NUMROW && board[downRow][colNum] == curPlayer) {
         consecutiveCount ++;
         downRow ++ ;
      }
      
      //counts consecutive going up
      int upRow = rowNum - 1;
      while (upRow >= 0 && board[upRow][colNum] == curPlayer) {
         consecutiveCount ++;
         upRow --;
      }
      
      return consecutiveCount;   
       
   }
   
   /*
   Parameter: Column #, row #
   Returns: the integer of horizontally consecutive pieces including that slot
   */
   public int horizontalConnect (int colNum, int rowNum) {
   
      int consecutiveCount = 1;
      
      //counts consecutive going right
      int rightCol = colNum + 1;
      while (rightCol < NUMCOL && board[rowNum][rightCol] == curPlayer) {
         consecutiveCount ++;
         rightCol++ ;
      }
      
      //counts consecutive going left
      int leftCol = colNum - 1;
      while (leftCol >= 0 && board[rowNum][leftCol] == curPlayer) {
         consecutiveCount ++;
         leftCol --;
      }
      
      return consecutiveCount;
   } 
   
   /*
   Parameter: Column #, row #
   Returns: the integer of diagonally (\) consecutive pieces including that slot
   */
   public int diagonalConnect1 (int colNum, int rowNum) {
   
      int consecutiveCount = 1;
      
      //checks consecutive count NW
      int upRow = rowNum - 1;
      int leftCol = colNum - 1;
      while (leftCol >= 0 && upRow >= 0 && board[upRow][leftCol] == curPlayer ) {
         consecutiveCount ++;
         upRow --;
         leftCol --;
      }   
      
      //counts consecutive SE
      int downRow = rowNum + 1;
      int rightCol = colNum + 1;
      while (downRow < NUMROW && rightCol < NUMCOL && board[downRow][rightCol] == curPlayer) {
         consecutiveCount ++;
         downRow ++;
         rightCol ++;
      } 
   
      return consecutiveCount;
      
   }   
   
   /*
   Parameter: Column #, row #
   Returns: the integer of diagonally (/) consecutive pieces including that slot
   */
   public int diagonalConnect2 (int colNum, int rowNum) {
   
      int consecutiveCount = 1;
      
      //checks consecutive count NE
      int upRow = rowNum - 1;
      int rightCol = colNum + 1;
      while (upRow >= 0 && rightCol < NUMCOL && board[upRow][rightCol] == curPlayer) {
         consecutiveCount ++;
         upRow --;
         rightCol ++;
      }   
      
      //counts consecutive SW
      int downRow = rowNum + 1;
      int leftCol = colNum - 1;
      while (downRow < NUMROW && leftCol >= 0 && board[downRow][leftCol] == curPlayer) {
         consecutiveCount ++;
         downRow ++;
         leftCol --;
      } 
   
      return consecutiveCount;
      
   }   
   /*
   Parameter: Gamefile Name
   Returns: T/F based on if game was successfully saved to a text file
   */
   public boolean saveToFile (String fileName) {
   
      try {
         BufferedWriter out = new BufferedWriter(new FileWriter(GAMEFILEFOLDER + "/" + fileName));
         
         //saves the current player into the text file
         out.write("" + curPlayer);
         out.newLine();
         
         String[][] savedBoard = new String[NUMROW][NUMCOL];
        
         //transfers the board in integer array into a a text file
         for (int i = 0; i < NUMROW; i++) {
            for (int j = 0; j < NUMCOL; j++) {
               savedBoard[i][j] = "" + board[i][j];
               out.write(savedBoard[i][j] + " ");           
            } 
            out.newLine();   
         }
      
         out.close();
      
         return true;   
      
      } catch (IOException iox) { //prints error message if game isn't saved
         System.out.println("Error saving game.");
      
         return false;
      }   
   
   }
   
   /*
   Parameter: Gamefile Name
   Returns: T/F based on if game was successfully loaded into the board array from a text file
   */
   public boolean loadFromFile (String fileName) {
      try {
         BufferedReader in = new BufferedReader(new FileReader(GAMEFILEFOLDER + "/" + fileName));
         
         //loads the correct player's turn after loading game
         curPlayer = Integer.parseInt(in.readLine());
         gui.setNextPlayer(curPlayer);
                  
         for (int i = 0; i < NUMROW; i++) {
            String[] savedBoardRow = in.readLine().split(" ");
            for (int j = 0; j < NUMCOL; j++) {
               board[i][j] = Integer.parseInt(savedBoardRow[j]);
            }              
         }
      
         in.close();
      
         return true;    
      
      } catch (IOException iox) { //loads error message is game cannot be loaded
         System.out.println("Error loading game.");
         return false;
      }   
      
   }

   public ConnectFour(ConnectFourGUI gui) {
      this.gui = gui;
      start();
   } 
   
   public ConnectFour() {
      start();
   } 
   
   
   /*
   Parameters: Column #
   Function: Uses previous methods to create the logic of the game  (find lowest slot, check for winner, ties, switch players, and update the GUI accordingly)
   */

   public void play (int column) { 
     
      //checks where piece is dropped and if the column is empty
      int row = locateEmptySlot(column);

      //places player piece if row is not full
      if (row != -1) {
         board[row][column] = curPlayer;
         gui.setPiece(row, column, curPlayer);
         
         //checks if there is 4 in a row
         if (horizontalConnect(column, row) >= WINCOUNT || verticalConnect(column, row) >= WINCOUNT || diagonalConnect1(column, row) >= WINCOUNT || diagonalConnect2(column, row) >= WINCOUNT) {
            gui.showWinnerMessage(curPlayer);
            gui.resetGameBoard();
            resetBoard();
         } 

         //checks if the board is full without having 4 in a row (tie)
         else if (boardFull()) {          
            resetBoard();
            gui.showTieGameMessage();
            gui.resetGameBoard();;
         } 

         //prepares the board for the next turn
         else {   
            gui.setNextPlayer(curPlayer);
            if (curPlayer == 1) {
               curPlayer = 2;
            } else {
               curPlayer = 1;
            }
            
            gui.setNextPlayer(curPlayer);  
         } 
      }
   }
   
   /*
   Function: Restarts the game board array and GUI
   */
   public void start () {
      board  = new int [NUMROW][NUMCOL];
      resetBoard();
      curPlayer = 1;
      gui.setNextPlayer(curPlayer);
   }
   
   /*
   Function: Syncs the GUI game board with the updated array board every turn
   */
   public void updateGameBoard () {
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            if (board[i][j] != EMPTY) {
               gui.setPiece(i, j, board[i][j]);
            }
         }
      }
   }        
} 