import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;
/**
 * Created by thomas on 23/06/17.
 */
public class Window extends JPanel implements ActionListener {
    private int s=24;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private JLabel lb;
    private int other;
    private myButton[] bt;
    private myButton last;
    private boolean turn;
    private String dir;
    private int c;
    private int total;
    private boolean server;
    private InetAddress addr;
    private int port;
    public Window(JLabel lb,InetAddress adr,int p) {
        port=p;
        addr=adr;

        this.lb = lb;
        dir = (System.getProperty("user.dir")) + "/img/";


        turn = true;
        bt = new myButton[s];

        //ArrayList<myButton> arr = new ArrayList<>();

        if (!(new File(dir).exists())){
            JOptionPane.showMessageDialog(this,
                    "Error can't find Images directory ,please make sure path "+dir + " exists!!");
            System.exit(0);
        }

        setLayout(new GridLayout(6, 4));

        Icon[] itemp = new Icon[12];

        itemp[0] = new ImageIcon(dir + "flower.gif");
        itemp[1] = new ImageIcon(dir + "apples.gif");
        itemp[2] = new ImageIcon(dir + "sky.gif");
        itemp[3] = new ImageIcon(dir + "flowers.gif");
        itemp[4] = new ImageIcon(dir + "yona.png");
        itemp[5] = new ImageIcon(dir + "creeper.png");
        itemp[6] = new ImageIcon(dir + "dog.png");
        itemp[7] = new ImageIcon(dir + "apple.png");
        itemp[8] = new ImageIcon(dir + "gril.jpg");
        itemp[9] = new ImageIcon(dir + "google.jpg");
        itemp[10] = new ImageIcon(dir + "fly.png");
        itemp[11] = new ImageIcon(dir + "green.jpg");

        setSize(500,400);
        setVisible(true);

        for (int i=0,j=0;i<s;j++) {

            try {
                bt[i]=new myButton(itemp[j%12],i);
                bt[i].addActionListener(this);
                bt[i+1]=new myButton(itemp[j%12],i+1);
                bt[i+1].addActionListener(this);
                bt[i].hideIcon();
                bt[i+1].hideIcon();

                i+=2;

            }
            catch (Exception e){
                System.out.println("Null is "+e.getCause());
            }


        }



    }
    public void actionPerformed(ActionEvent e){
        if (!turn) return;
        myButton x=(myButton) e.getSource();
        if (x==last) return;

        x.showIcon();
        c++;
        if (c==2){
            turn =false;

           c=0;
            ResetThread c=new ResetThread(x,last);
            if (x.getIconFrom().hashCode()!=last.getIconFrom().hashCode()) {
                c.start();
                try {
                    out.writeObject(new Msg(1, "Fliiped " +x.getIndex(), x.getIndex()));
                }
                catch(IOException r){
                    System.out.print(r.getMessage());
                }

                last=null;
                lb.setText("Play 2 turn, You Fliped : "+total);
                return;
            }
            else {
                total++;
                last.setEnabled(false);
                last=null;
                x.setEnabled(false);


                if (other+total==12) {
                    try {
                        out.writeObject(new Msg(2, "Fliiped " +x.getIndex(), x.getIndex()));
                    }
                    catch(IOException r){
                        System.out.print(r.getMessage());
                    }

                    System.out.println("Over\n"+total+" "+other);
                    server=false;
                    if (total>6){
                        lb.setText("Game Over You WON!!! with "+total+" Flips");
                    }
                    else{
                        lb.setText("You lost Ya 0");
                    }

                    return;
                }

            }
            try {
                out.writeObject(new Msg(1, "Fliiped " +x.getIndex(), x.getIndex()));
            }
            catch(IOException r){
                System.out.print(r.getMessage());
            }
            lb.setText("Play 2 turn, You Fliped : "+total);

        }



        else{
            try {
                out.writeObject(new Msg(1, "Fliiped " +x.getIndex(), x.getIndex()));
            }
            catch(IOException r){
                System.out.print(r.getMessage());
            }
            last=x;


        }




    }


    public void startGame(){

        boolean flag=true;
        other=0;
        int last=-1,count=0;
        turn =false;
        while (flag){
            try (Socket socket = new Socket(addr, port)){


                System.out.print("Socket Done");
                lb.setText("Waiting for opponent!");
                server=true;
                out=new ObjectOutputStream(socket.getOutputStream());
                in=new ObjectInputStream(socket.getInputStream());

                flag=false;
                System.out.print("Before");
                Msg board = (Msg) in.readObject();
                System.out.print("After");
                build(board.arr);
                lb.setText("Player 2 has joined !");

                while (true){
                    Msg x=(Msg) in.readObject();
                    System.out.println("Got a msg type->"+x.type);
                    if (x.type==0){
                        turn =false;

                        bt[x.bt].showIcon();
                        count++;
                        if (count==2){
                            count=0;
                            turn=true;
                            if ( bt[x.bt].getIconFrom().hashCode()==bt[last].getIconFrom().hashCode() ){
                                bt[x.bt].setEnabled(false);
                                bt[last].setEnabled(false);
                                other++;
                            }
                            else{
                                ResetThread c=new ResetThread( bt[x.bt],bt[last]);
                                c.start();
                            }
                        }
                        else{
                            last=x.bt;
                        }

                    }
                    if (x.type==1){
                        turn =true;
                        lb.setText("Your Turn! You Fliped : "+total);
                    }
                    if (x.type==2){
                        server=false;
                        turn=false;
                        System.out.println(x.bt);
                        bt[x.bt].showIcon();
                        bt[x.bt].setEnabled(false);
                        bt[last].setEnabled(false);
                        if (total>6){
                            lb.setText("Game Over You WON!!! with "+total+" Flips");
                        }
                        else{
                            lb.setText("You lost Ya 0");
                        }
                        return;
                    }
                    if (x.type==4){
                        turn=false;
                        lb.setText("Oppennet has quit..game is over relunch window to restart\n");
                    }


                }
            }
            catch (IOException e) {
                try {
                    System.out.println(" In IO"+e.getMessage());
                    if(server){
                        lb.setText("Connection lost!");
                        Thread.sleep(2000);
                        return;
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException ef) {
                    System.exit(0);
                }
            }
            catch (ClassNotFoundException e){
                System.out.print(" In Class");
                    System.exit(0);
            }

        }



    }
        private void build(int[] arr){

            System.out.print("In Build"+arr.length);
            for (int i=0;i<arr.length;i++){
                System.out.printf("%d,",arr[i]);


                this.add(bt[arr[i]]);

            }
            setSize(501,501);


        }

}

