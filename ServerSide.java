import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;//.ServerSocket;

/**
 * Created by thomas on 26/06/17.
 */
public class ServerSide extends Thread{
    private ServerSocket socket;
    public ServerSide(){
        try {
            socket = new ServerSocket(4444);
        }
        catch (IOException e){

            System.out.print("Error Can't create socket server down! -> "+e.getMessage());
            System.exit(0);
        }
    }
    public void run(){
        Socket p1;
        Socket p2;
        int total=0;
            while (true) {
                try {
                    System.out.println("Waiting for 2 new playes!");
                    p1 = socket.accept();

                    System.out.println("Player 1 has joined!");
                    p2 = socket.accept();
                    System.out.println("Player 2 has joined!");
                    Game g = new Game(p1,p2);
                    g.start();
                    total++;
                    System.out.print("Game "+total+" Has started..\n");

                }
         catch(IOException e){
                    System.out.println("Error!!! at sockets");
                    e.printStackTrace();
                }

            }
        }



    }


