package gamma;

import PrologDB.Tuple;
import gammaSupport.Connector;
import static gammaSupport.GammaConstants.splitLen;
import gammaSupport.ReadEnd;
import gammaSupport.ThreadList;
import gammaSupport.WriteEnd;

/** class for do nothing box - copy input stream to output stream 
 *  useful for debugging and regression tests */
public class DoNothing extends Thread implements gammaSupport.GammaConstants {
    ReadEnd in;
    WriteEnd out;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public DoNothing(Connector in, Connector out) {
        this.in = in.getReadEnd();
        out.setTableSchema(in.getTableSchema());
        this.out = out.getWriteEnd();
        
        ThreadList.add(this);
    }
    
    @Override
    public void run() {
        // to do  
        while (true) {
            Tuple t = in.getNextTuple();
            if (t == null) {
//                in.close();
                break;
            }
            out.putNextTuple(t);
        }
        out.close();
    }
}
