package gamma;

import gammaSupport.*;
import PrologDB.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * class for primitive HJOIN
 */
public class HJoin extends Thread implements gammaSupport.GammaConstants {

    /* 1. Read all of stream A into a main memory hash table (hash on join key)
       2. Read B stream one tuple at a time:
            hash join key of B's tuple and join it to all A tuple's with the same join key
       3. Linear algo in that each A, B tupe is read only once
    
    Uses HSPLIT, BLOOM, BFILTER, HJOIN, MERGE
    Merge takes in several In connectors and one out connector so HJoin needs to
    Put out in connectors.
    To make a connector, we have to make a Table Schema for whatever you're using. 
     */

    private final ReadEnd inA, inB;
    private final WriteEnd w;
    private HashMap<String, LinkedList<Tuple>> hashmap;
    private final String joinkeyA, joinkeyB;
    private final TableSchema jschema;

    @SuppressWarnings("LeakingThisInConstructor")
    public HJoin(Connector A, Connector B, Connector out, String joinkeyA, String joinkeyB) {
        this.joinkeyA = joinkeyA;
        this.joinkeyB = joinkeyB;
        hashmap = new HashMap<>();
        inA = A.getReadEnd();
        inB = B.getReadEnd();
        w = out.getWriteEnd();

        //Step 2: Make table thingy
        TableSchema tsA = A.getTableSchema();
        TableSchema tsB = B.getTableSchema();
        jschema = tsA.crossProduct(tsB);
//        tsA.print();
//        tsB.print();
//        jschema.print();
        out.setTableSchema(jschema);
        
        // Step 3: don't forget leaking constructor
        ThreadList.add(this);
    }

    @Override
    public void run() {
        //Step one: fill table with A.
        while (true) {
            Tuple t = inA.getNextTuple();

            if (t == null) {
                inA.close();
                break;
            }

            String value = t.get(joinkeyA);
            // to do
            if (!hashmap.containsKey(value)) {
                hashmap.put(value, new LinkedList<>());
            }
            hashmap.get(value).add(t);
        }

        //Step two: Read B one Tuple at a Time
        while (true) {
            Tuple t = inB.getNextTuple();

            if (t == null) {
                inB.close();
                w.close();
                break;
            }
            // to do;
            String value = t.get(joinkeyB);
            if (!hashmap.containsKey(value)) {
                continue;
            }
            for (Tuple tA : hashmap.get(value)) {
                Tuple tJoin = new Tuple(jschema);
                tJoin.setValues(tA);
                tJoin.setValues(t);
                w.putNextTuple(tJoin);
            }
        }
        // maybe more to do.
        w.close();
    }
}
