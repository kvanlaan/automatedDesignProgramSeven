package gamma;

import PrologDB.TableSchema;
import PrologDB.Tuple;
import gammaSupport.ReadEnd;
import gammaSupport.WriteEnd;
import gammaSupport.Connector;
import gammaSupport.GammaError;
import static gammaSupport.GammaError.tableSchemasNoMatch;
import gammaSupport.ThreadList;

/**
 * ordered merge box
 */
public class OMerge extends Thread implements gammaSupport.GammaConstants {

    ReadEnd[] in;
    WriteEnd out;
    boolean done[];
    String sortkey;

    /**
     * merge box constructor
     *
     * @param in -- input array of connectors
     * @param out -- output connector
     */
    OMerge(Connector in[], Connector out, String sortkey) {

        this.sortkey = sortkey;
        this.in = new ReadEnd[splitLen];
        done = new boolean[splitLen];
        for (int i = 0; i < splitLen; i++) {
            this.in[i] = in[i].getReadEnd();
            done[i] = false;
        }

        this.out = out.getWriteEnd();
        TableSchema ts = in[0].getTableSchema();
        for (int i = 1; i < splitLen; i++) {
            if (in[i] != null && !ts.equals(in[i].getTableSchema())) {
                throw GammaError.toss(tableSchemasNoMatch, in[0].getName());
            }
        }
        out.setTableSchema(ts);

        for (int i = 0; i < splitLen; i++) {
            in[i].verifyField(sortkey, "Sort");
        }
        ThreadList.add(this);
    }

    /**
     * OMerge body
     */
    @Override
    public void run() {
        Tuple ta[] = new Tuple[splitLen];

        // Step 1: load up array with tuples from each connector
        for (int i = 0; i < splitLen; i++) {
            ta[i] = in[i].getNextTuple();
        }

        // Step 2: now cycle to find the next tuple to output
        while (true) {
            int next = -1;
            String smallest = null;
            for (int i = 0; i < splitLen; i++) {
                if (ta[i] != null) {
                    String v = ta[i].get(sortkey);
                    if (smallest == null || lss(v, smallest)) {
                        smallest = v;
                        next = i;
                    }
                }
            }
            // Step 2.1: exit if we're done
            if (next == -1) {
                break;
            }
            out.putNextTuple(ta[next]);
            ta[next] = in[next].getNextTuple();
        }
        for (int i = 0; i < splitLen; i++) {
            in[i].close();
        }
        out.close();
    }

    static boolean lss(String a, String b) {
        return (a.compareTo(b) < 0);
    }
}
