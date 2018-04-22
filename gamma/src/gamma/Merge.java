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
    @SuppressWarnings("LeakingThisInConstructor")
    public Merge(Connector in[], Connector out) {
        // to do
        this.in = new ReadEnd[splitLen];
        for (int i = 0; i < splitLen; i ++) {
            this.in[i] = in[i].getReadEnd();
        }
        
        out.setTableSchema(in[0].getTableSchema());
        this.out = out.getWriteEnd();
        
        this.done = new boolean[splitLen];
        for (int i = 0; i < splitLen; i ++) {
            done[i] = false;
        }
        
        ThreadList.add(this);
    }

    /** Merge body */
    @Override
    public void run() {
        // to do 
        int counter = 0;
        while (numberDone < splitLen) {
            int i = counter % splitLen;
            if (done[i]) {
                counter ++;
                continue;
            }
            
            Tuple t = in[i].getNextTuple();
            if (t == null) {
                done[i] = true;
                numberDone ++;
//                in[i].close();
            } else {
                out.putNextTuple(t);
            }
            counter ++;
        }
        out.close();
    }
}
