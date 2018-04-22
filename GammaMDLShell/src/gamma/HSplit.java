package gamma;

import PrologDB.TableSchema;
import PrologDB.Tuple;
import gammaSupport.BMap;
import gammaSupport.Connector;
import gammaSupport.ReadEnd;
import gammaSupport.ThreadList;
import gammaSupport.WriteEnd;

/** primitive HSplit */
public class HSplit extends Thread implements gammaSupport.GammaConstants {

    private final String joinkey;
    private final ReadEnd in;
    private final WriteEnd[] out;

    /**
     * 
     * @param in -- input connector
     * @param out -- output connector
     * @param hashkey on which hashing is to be done, a field of input connector
     */
    HSplit(Connector in, Connector[] out, String hashkey) {

        this.joinkey = hashkey;
        this.in = in.getReadEnd();
        this.out = new WriteEnd[splitLen];
        // fill in
    }

    /** body of HSplit */
    @Override
    public void run() {
      // to do
    }
}
