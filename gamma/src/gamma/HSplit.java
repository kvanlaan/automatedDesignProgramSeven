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
    @SuppressWarnings("LeakingThisInConstructor")
    public HSplit(Connector in, Connector[] out, String hashkey) {

        this.joinkey = hashkey;
        this.in = in.getReadEnd();
        this.out = new WriteEnd[splitLen];
        // fill in
        for (int i = 0; i < splitLen; i ++) {
            out[i].setTableSchema(in.getTableSchema());
            this.out[i] = out[i].getWriteEnd();
        }
        
        ThreadList.add(this);
    }

    /** body of HSplit */
    @Override
    public void run() {
        // to do  
        while (true) {
            Tuple t = in.getNextTuple();
            if (t == null) {
//                in.close();
                break;
            }
            int hash = myhash(t.get(this.joinkey));
            out[hash].putNextTuple(t);
        }
        
        for (int i = 0; i < splitLen; i ++) {
            out[i].close();
        }
    }
    
    int myhash(String s) {
        return (Math.abs(s.hashCode()) % splitLen);
    }
}
