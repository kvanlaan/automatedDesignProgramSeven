package gammaSupport;

import PrologDB.Column;
import PrologDB.TableSchema;
import PrologDB.Tuple;
import static gammaSupport.GammaError.tupleHasWrongNumCols;

/** additional operations on Tuples */
public class TupleSupport {
    
    // serialized tuples have their fields separated by this character
    private static final String separator="#";
    
    /**
    * this method serializes a tuple into a string; note a tuple (v1,v2,v3)
    * yields string v1#v2#v3
     * @param t - tuple to serialize
     * @return -- string serialization of tuple
    */
    public static String serialize(Tuple t) {
        if (t.size()!=t.getSchema().size()) {
            throw GammaError.toss(tupleHasWrongNumCols, t.getSchema().getName(), t.size()+"");
        }
        String result = "";
        for (Column c : t.getSchema().getColumns()) {
            String val = t.get(c.getName());
            if (val.equals("")) val = "''";  // corner case
            result = result + val + separator;
        }
        return result;
    }


    /** unserialize a string into a tuple
     * @param ts -- TableSchema of tuple
     * @param line -- serialized tuple
     * @return  tuple counterpart
     */
    public static Tuple unserialize(TableSchema ts, String line) {
        String[] st = line.split(separator);
        if (st.length != ts.size()) {
            GammaError.toss(tupleHasWrongNumCols, ts.getName(), st.length+"");
        }
        int fieldNumber = 0;
        Tuple t = new Tuple(ts);
        for (Column c : t.getSchema().getColumns()) {
            String val = st[fieldNumber++];
            if (val.equals("''")) val = "";
            t.set(c.getName(), val);
        }
        return t;
     }
}
