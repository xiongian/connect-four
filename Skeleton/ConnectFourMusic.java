/* * * * * * * * * * * * * * * * * * * * * * * * * * 
* The ConnectFourMusic class                       *
*                                                  *   
* Ian Xiong                                        *
*                                                  *
* ICS 3U1                                          *
*                                                  *   
* Ms. Lam                                          *      
*                                                  *
* Last updated: June 12, 2023                      *
*                                                  *
* This class allows background music to be played  *
* in the game.                                     *
*                                                  *
* * * * * * * * * * * * * * * * * * * * * * * * * */
//imports
import javax.sound.sampled.*;
import java.io.*;

public class ConnectFourMusic {

   /*
   Function: Streams background music whenever called
   */
   public static void playMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    
      //creates a new file that holds the music file 
      File musicFile = new File("boonie_music.wav");
   
      //creates an object that gets the music file contents
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
         
      //adds music file to a clip, opening it
      Clip clip = AudioSystem.getClip();
      clip.open(audioStream);
   
      //adding a listener that loops the audio file after finish playing it
      clip.addLineListener(
         event -> {
            if (event.getType() == LineEvent.Type.STOP) {
               clip.setFramePosition(0);
               clip.start();
            }
         });
         
      clip.start();
   }
}