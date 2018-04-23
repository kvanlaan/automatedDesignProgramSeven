package gamma;

import gammaSupport.Connector;
import gammaSupport.ReadEnd;
import gammaSupport.ThreadList;

/** PrintMap box -- useful for debugging and regression tests */
public class PrintMap extends Thread implements gammaSupport.GammaConstants {
    Connector in;
    ReadEnd re;

    /**
     * Print constructor
     * @param in  -- input connector
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public PrintMap(Connector in) {
        this.in = in;
        this.re = in.getReadEnd();
        
        ThreadList.add(this);
    }

    /** print body */
    @Override
    public void run() {
        try {
            String bm_serialized = re.getSerializedMap();
            System.out.println("map : " + bm_serialized);
            re.close();
        } catch (Exception e) {
            System.out.println("ERROR! readEnd getSerializedMap failed. ");
        }
    }
}
