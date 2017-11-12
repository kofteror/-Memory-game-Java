import javax.swing.*;

/**
 * Created by thomas on 25/06/17.
 */
public class myButton extends JButton {
    private Icon icon;



    private int index;
    public myButton(Icon i,int idx){
        super("X",i);
        index=idx;

        icon=i;
        hideIcon();
    }
    public void showIcon() {
        super.setIcon(icon);
    }
    public void hideIcon(){

        setIcon(null);
    }
    public Icon getIconFrom(){
        return  icon;
    }
    public int getIndex() {
        return index;
    }
}
