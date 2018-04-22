package gamma;

import PrologDB.TableSchema;
import PrologDB.Tuple;
import gammaSupport.ReadEnd;
import gammaSupport.WriteEnd;
import gammaSupport.Connector;
import gammaSupport.GammaDebug;
import static gammaSupport.GammaDebug.streamOutput;
import gammaSupport.GammaError;
import static gammaSupport.GammaError.tableSchemasNoMatch;
import gammaSupport.ThreadList;

/** merge box */
public class Merge extends Thread implements gammaSupport.GammaConstants {

    ReadEnd[] in;
    WriteEnd out;
    boolean[] done;
    int numberDone = 0;

    /**
     * merge box constructor
     * @param in -- input array of connectors
     * @param out -- output connector
     */
    Merge(Connector in[], Connector out) {

        // to do
    }

    /** Merge body */
    @Override
    public void run() {
        // to do 
        out.close();
    }
}
