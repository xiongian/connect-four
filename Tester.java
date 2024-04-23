
import java.io.*;

public class Tester {

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
   int board[][] = new int [NUMROW][NUMCOL];
   int curPlayer = 0; 

  //determine number of consecutive pieces diagonally (/)
   public int diagonalConnect2 (int colNum, int rowNum) {
   
      int consecutiveCount = -1;
      
      //checks consecutive count NE
      int upRow = rowNum;
      int rightCol = colNum;
      while (board[upRow][rightCol] == curPlayer && upRow >= 0 && rightCol < NUMCOL) {
         consecutiveCount ++;
         upRow --;
         rightCol ++;
      }   
      
      //counts consecutive SW
      int downRow = rowNum;
      int leftCol = colNum;
      while (board[downRow][leftCol] == curPlayer && downRow <= NUMROW && leftCol >= 0) {
         consecutiveCount ++;
         downRow ++;
         leftCol --;
      } 
   
      return consecutiveCount;
      
   }   



   //TEST
   public static void main (String[] args) {
   
   Tester cf = new Tester();
   
      System.out.print(cf.diagonalConnect2(0, 0));

   }
   
   }