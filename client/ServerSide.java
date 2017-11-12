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

            System.out.print("Error "+e.getMessage());
        }
    }
    public void run(){
        Socket s;
        try {
            while (true) {

                    s = socket.accept();
                    Game g=new Game(s);
                    g.run();
                    s= socket.accept();
                    g.addPlayer(s);


            }
        }
        catch(IOException e){
            System.out.print("ERRO");
        }


    }

}
