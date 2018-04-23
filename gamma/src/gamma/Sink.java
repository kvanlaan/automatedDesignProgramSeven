package gamma;

import PrologDB.Tuple;
import gammaSupport.Connector;
import gammaSupport.ReadEnd;
import gammaSupport.ThreadList;

/** sink box class -- read input connector until nothing is left to read;
 * do nothing otherwise.  useful for debugging and regression tests */
public class Sink extends Thread implements gammaSupport.GammaConstants {
    ReadEnd in;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public Sink(Connector in) {
        this.in = in.getReadEnd();
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
        }
    }
}
