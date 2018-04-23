package gamma;

import gammaSupport.BMap;
import gammaSupport.Connector;
import gammaSupport.ThreadList;
import gammaSupport.WriteEnd;

/** class for Bloom Simulator -- it takes no connector inputs (but uses files 
 * instead for its inputs).  Useful for debugging and regression tests).  
 **/
public class BloomSimulator extends Thread implements gammaSupport.GammaConstants {
    WriteEnd we;
    String filter;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public BloomSimulator(Connector m, String filter) {
        this.we = m.getWriteEnd();
        this.filter = filter;
        
        ThreadList.add(this);
    }
    
    @Override
    public void run(){
        we.putNextBMap(new BMap(filter));
        we.close();
    }
}
