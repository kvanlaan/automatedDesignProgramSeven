package gamma;

import gammaSupport.*;
import PrologDB.Tuple;
import java.util.ArrayList;
import java.util.LinkedList;
/** code for Bloom */
public class Bloom extends Thread implements gammaSupport.GammaConstants {
    /* 1. Clear bitmap M
       2. read each A tuple, hash its join key, and mark corresponding bit in M
       3. Output each tuple A
       4. aftr all A tuples read, output M
    
    M will go to BFILTER, while the tupes of A will go to HJOIN
    */
    
    private final Connector A, out_join, out_bfilter;
    private final String hashKey;
    private final ArrayList<Tuple> tuples;
    private BMap map;

    public Bloom(Connector A, Connector out_join, Connector out_bfilter, String hashKey){
        this.A = A;
        this.out_join = out_join;
        this.out_bfilter = out_bfilter;
        this.hashKey = hashKey;
        map = new BMap();
        tuples = new ArrayList<>();
    }
    
    
    public void run(){
        // to do
    }
}
