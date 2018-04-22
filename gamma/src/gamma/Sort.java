package gamma;

import PrologDB.Tuple;
import gammaSupport.Connector;
import gammaSupport.ReadEnd;
import gammaSupport.ThreadList;
import gammaSupport.WriteEnd;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/** primitive sort boxes */
public class Sort extends Thread implements gammaSupport.GammaConstants {

    private final ReadEnd in;
    private final WriteEnd out;
    private final String sortKey;
    private final LinkedList<Tuple> listOfTuples;

    /** primitive sort constructor
     * @param in -- input connector
     * @param out -- output connector
     * @param sortKey -- sort key, which must be a field of the input connector
     */
    public Sort(Connector in, Connector out, String sortKey) {
        this.in = in.getReadEnd();
        this.out = out.getWriteEnd();
        this.sortKey = sortKey;
        listOfTuples = new LinkedList<>();
        out.setTableSchema(in.getTableSchema());

        in.verifyField(sortKey, "Sort");
        ThreadList.add(this);
    }

    /** sort body */
    @Override
    public void run() {
        // Step 1: inhale tuples
        while (true) {
            Tuple t = in.getNextTuple();
            if (t == null) {
                in.close();
                break;
            }
            listOfTuples.add(t);
        }

        // Step 2: sort them
        Comparator<Tuple> cmp = (Tuple x, Tuple y) -> x.get(sortKey).compareTo(y.get(sortKey));
        Collections.sort(listOfTuples, cmp);

        // Step 3: output sorted tuples
        for (Tuple t : listOfTuples) {
            out.putNextTuple(t);
        }
        out.close();
    }
}
