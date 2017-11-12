import javax.swing.plaf.synth.SynthUI;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by thomas on 26/06/17.
 */
public class Game extends Thread{
    private Socket player1;
    private Socket player2;
    private  ObjectInputStream inP1;
    private ObjectOutputStream outP1;
    private  ObjectInputStream inP2;
    private ObjectOutputStream outP2;
    public Game(Socket player) {
        try {
            player1 = player;
            inP1 = new ObjectInputStream(player1.getInputStream());
            outP1 = new ObjectOutputStream(player1.getOutputStream());

            player2=null;
        }
        catch(IOException e){

            System.exit(0);
        }
//        catch (ClassNotFoundException e){
//            System.exit(0);
//        }
    }
    public void run(){

        System.out.print("Thread started\n");
    }
    public void  addPlayer(Socket p) {
        try {
            player2 = p;
            inP2 = new ObjectInputStream(player2.getInputStream());
            outP2 = new ObjectOutputStream(player2.getOutputStream());
        }
        catch (IOException e){
            System.exit(0);
        }
    }
}
