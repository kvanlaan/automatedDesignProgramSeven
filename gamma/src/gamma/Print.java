package gamma;

import PrologDB.Tuple;
import gammaSupport.Connector;
import gammaSupport.ReadEnd;
import gammaSupport.ThreadList;

/** Print Box */
public class Print extends Thread implements gammaSupport.GammaConstants {

    Connector in;
    ReadEnd re;

    /**
     * Print constructor
     * @param in  -- input connector
     */
    public Print(Connector in) {
        this.in = in;
        this.re = in.getReadEnd();
        
        ThreadList.add(this);
    }

    /** print body */
    @Override
    public void run() {
        in.tableSchema.print(System.out);

        while (true) {
            Tuple t = re.getNextTuple();
            if (t == null) {
                re.close();
                break;
            }
            t.print(System.out);
        }
    }
}
