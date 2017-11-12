import java.io.Serializable;

/**
 * Created by thomas on 26/06/17.
 */
public   class  Msg implements Serializable {
    public int type;
    public String msg;
    public int[] arr;
    public int bt;
    public Msg(int tp,String ms){
        type=tp;
        msg=ms;
        arr=null;

    }
    public Msg(int tp,String ms,int[] a){
        type=tp;
        msg=ms;
        arr=a;

    }
    public Msg(int tp,String ms,int card){
        type=tp;
        msg=ms;
        bt=card;

    }


}