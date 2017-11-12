import com.sun.deploy.uitoolkit.impl.awt.AWTPluginEmbeddedFrameWindow;
import javafx.embed.swing.JFXPanel;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by thomas on 23/06/17.
 */
public class Main {
    public static void main(String argv[]){
        InetAddress adr;
        int port;
      while(true) {
            JFrame app = new JFrame();
            JLabel lb = new JLabel("Waiting for connection to server Please wait!");
            lb.setSize(500, 100);
            while(true) {
                try {

                     adr = InetAddress.getByName(JOptionPane.showInputDialog("Enter host address"));
                     port=Integer.parseInt(JOptionPane.showInputDialog("Enter port"));
                    break;
                } catch (UnknownHostException e) {
                    JOptionPane.showMessageDialog(null, "Wrong adress try again","Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            Window x = new Window(lb,adr,port);


            lb.setHorizontalAlignment(SwingConstants.CENTER);

            app.add(lb, BorderLayout.NORTH);
            app.add(x);

            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            app.setSize(500, 500);
            app.setVisible(true);
            x.startGame();
            app.setVisible(false);
           app.dispose();
      }

    }
}
