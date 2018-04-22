/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamma;

import PrologDB.Table;
import PrologDB.TableSchema;
import PrologDB.Tuple;
import gammaSupport.Connector;
import java.io.*;
import java.util.*;
import gammaSupport.ThreadList;
import gammaSupport.WriteEnd;

/**
 *
 * @author katrinavanlaan
 */
public class ReadRelation extends Thread implements gammaSupport.GammaConstants {
    
    BufferedReader input;
    TableSchema r;
    Table tab;
    WriteEnd w;
    
    /**
     * Read Table with filename and send all of its tuples to connector out
     *
     * @param filename -- path name of tfile
     * @param out -- connector
     */    
    @SuppressWarnings("LeakingThisInConstructor")
    public ReadRelation(String fileName, Connector out) {
        this.w = out.getWriteEnd();
        tab = Table.readTable(fileName);
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

    public static StringTokenizer readFile(String inputFile) {
        try {
            File file = new File(inputFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            fileReader.close();

            StringTokenizer st = new StringTokenizer(stringBuffer.toString());
            System.out.println("---- Split by space ------");
            while (st.hasMoreElements()) {
                System.out.println(st.nextElement());
            }
            return st;
        } catch (IOException e) {
            e.printStackTrace();

        }
        StringTokenizer st2 = new StringTokenizer("");
        return st2;
    }

}
