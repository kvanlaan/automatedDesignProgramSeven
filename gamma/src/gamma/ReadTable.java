package gamma;

import PrologDB.Table;
import PrologDB.Tuple;
import PrologDB.TableSchema;
import gammaSupport.Connector;
import gammaSupport.WriteEnd;
import gammaSupport.ThreadList;
import java.io.*;

/** ReadTable box a.k.a. ReadRelation box **/
public class ReadTable extends Thread implements gammaSupport.GammaConstants {

    BufferedReader input;
    TableSchema r;
    Table tab;
    WriteEnd w;

    /**
     * Read Table with filename and send all of its tuples to connector out
     *
     * @param filename -- path name of the file
     * @param out -- connector
     */
    public ReadTable(Connector out, String filename) {
        this.w = out.getWriteEnd();
        tab = Table.readTable(filename);
        if(tab == null) System.out.println("Bruh no table:(");
        out.setTableSchema(tab.getSchema());
        
        ThreadList.add(this);
    }

    /** ReadTable Body */
    @Override
    public void run() {
        for (Tuple t : tab.tuples()) {
            w.putNextTuple(t);
        }
        w.close();
    }
}
