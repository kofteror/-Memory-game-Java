import javax.swing.*;

/**
 * Created by thomas on 23/06/17.
 */
public class ResetThread extends Thread {
    private JButton one;
    private JButton two;
    public ResetThread(JButton a,JButton b){
        one=a;
        two=b;

    }
    public void run(){
        try {
            sleep(300);
        }
        catch(InterruptedException e){

        }
        one.setIcon(null);
        two.setIcon(null);
    }
}
