
import com.sun.xml.internal.ws.developer.MemberSubmissionEndpointReference;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

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
    public int[] arr;
    public Game(Socket p1,Socket p2) {
        Random rand=new Random();
        arr=new int[24];
        ArrayList<Integer> a=new ArrayList<>(24);
        for (int i=0;i<24;i++){
            a.add(i);
        }

        int j=0;
        while (a.size()>0){
            arr[j++]=a.remove(rand.nextInt(a.size()));
        }

        try {
            player1 = p1;
            inP1 = new ObjectInputStream(player1.getInputStream());
            System.out.print("Input Stream for player 1 has been started");
            outP1 = new ObjectOutputStream(player1.getOutputStream());
            System.out.print("Output Stream for player 1 has been started");
            player2=p2;

            inP2 = new ObjectInputStream(player2.getInputStream());
            System.out.print("Input Stream for player 2 has been started");
            outP2 = new ObjectOutputStream(player2.getOutputStream());
            System.out.println("Output Stream for player 2 has been started");
            try {
                outP1.writeObject(new Msg(1, "Hello", arr));
                outP2.writeObject(new Msg(1, "Hello", arr));
            }
            catch (IOException e){
                System.exit(0);
            }

        }
        catch(IOException e){
            System.out.print("Out Thread");
            exitThread(1);
            System.exit(0);
        }

        System.out.print("Done GaME");

    }
    private  void exitThread(int x){
        try {
            player1.close();
            player2.close();
        }
        catch (IOException e){
            System.exit(0);
        }
        if (x==0){
            System.out.print("Game is over\n");
        }
        if (x==1){
            System.out.print("Game is terminated other played disconnected!\n");

        }
        return;
    }
    public void run(){
        System.out.println("Socket ports : Player 1 : "+player1.getPort()+" Player 2: "+player2.getPort());
        int turn=-1;
        try {
            outP1.writeObject(new Msg(1, "Play"));
        }
        catch (IOException e){
            exitThread(1);
            return;
        }
        while (true){
            Msg x;
            Msg y;
            try{
                for (int i=0;i<2;i++) {
                    turn=1;
                    x = (Msg) inP1.readObject();
                    System.out.println("Got a msg From 1 type->"+x.type);
                    turn=2;
                    if (x.type == 1) {
                        System.out.println(x.msg);

                        outP2.writeObject(new Msg(0, "fliped", x.bt));
                    }
                    if (x.type==2){
                        outP2.writeObject(new Msg(2,"Game Over",x.bt));
                       exitThread(0);
                       return;

                    }

                }
                outP2.writeObject(new Msg(1, "fliped",0));
                for (int i=0;i<2;i++) {
                    turn=2;
                    y = (Msg) inP2.readObject();
                    System.out.println("Got a msg From 2 type->"+y.type);
                    turn=1;
                    if (y.type == 1) {
                        System.out.println(y.msg);
                        outP1.writeObject(new Msg(0, "fliped", y.bt));
                    }
                    if (y.type==2){
                        System.out.println(y.msg);
                        outP1.writeObject(new Msg(2,"Game Over",y.bt));
                        exitThread(0);
                        return;

                    }

                }
                outP1.writeObject(new Msg(1, "fliped",0));


            }
            catch (IOException e){

                System.out.print("Game End!!!\n");
                try {
                    System.out.println("Sendiing kill msg to "+turn);
                    if (turn == 1) {
                        outP2.writeObject(new Msg(4, "error"));
                    } else {
                        outP1.writeObject(new Msg(4, "error"));
                    }
                }

                catch(IOException err){
                    System.out.print("Terminated!!\n");
                }
                exitThread(0);
                return;
            }
            catch (ClassNotFoundException m){}

        }

    }

    }

