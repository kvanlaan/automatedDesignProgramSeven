package gamma;
import gammaSupport.*;
import PrologDB.Tuple;
import java.util.ArrayList;
/** create a BFilter */
public class BFilter extends Thread implements gammaSupport.GammaConstants {
    /* 1. Read Bitmap M
       2. read each tpe of B, hash its join key: if corresponding bit in M is not set, discard tuple!
       3. else output tuple
    
    Outputs a filtered set of B tuples to go to HJOIN.
    */
    private final ReadEnd inb;
    private final ReadEnd inm;
    private final WriteEnd out;
    private final String hashKey;
    private final ArrayList<Tuple> tuples;
//    private BMap map;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public BFilter(Connector bIn, Connector mapIn, Connector output, String hashkey){
        this.inb = bIn.getReadEnd();
        this.inm = mapIn.getReadEnd();
        this.out = output.getWriteEnd();
//        this.map = inm.getNextBMap();
        this.hashKey = hashkey;
        tuples = new ArrayList<>();
        
        output.setTableSchema(bIn.getTableSchema());
        
        ThreadList.add(this);
    }
    
    @Override
    public void run(){
        //For every tuple in B, get the map bool value and add the tuple IF
        //the bit is true
        
        BMap map = inm.getNextBMap();
        inm.close();
        
        while(true) {
            Tuple t = inb.getNextTuple();
            if (t == null) {
                inb.close();
                break;
            }
            // do something with t

            String value = t.get(hashKey);
            boolean bit = map.getBit(value);
            
            if(bit){
                tuples.add(t);
            }
        }
        
        // Step 2: output tuples of b that make it past the bitmap check
        for (Tuple t : tuples) {
            out.putNextTuple(t);
        }
        out.close();
    }
}
