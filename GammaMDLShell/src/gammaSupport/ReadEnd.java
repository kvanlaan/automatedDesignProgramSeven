package gammaSupport;

import PrologDB.Tuple;
import static gammaSupport.GammaError.pipeCloseError;
import static gammaSupport.GammaError.pipeReadError;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * this is an adaptor that maps a Java pipe interface to a TupleStream interface
 */
public class ReadEnd {

    BufferedReader b;
    Connector c;

    /**
     * returns read end of connector c
     *
     * @param c -- connector
     */
    ReadEnd(Connector c) {
        this.c = c;
        b = c.in;
    }

    /**
     * close read end
     */
    public void close() {
        try {
            b.close();
        } catch (IOException e) {
            throw GammaError.toss(pipeCloseError, c.getName(), e.getMessage());
        }
    }

    /**
     *
     * @return next tuple from read end; null if end of pipe reached
     */
    public Tuple getNextTuple() {
//        System.out.println("Reading tuple");
        try {
//            System.out.println("trying");
//            System.out.println(b.ready());
            String serializedTuple = b.readLine();
            if (serializedTuple == null) {
//                System.out.println("returning null tuple");
                return null;
            }
            
            Tuple t = TupleSupport.unserialize(c.getTableSchema(), serializedTuple);
//            System.out.println("made a real tuple");
            return t;
        } catch (IOException e) {
//            System.out.println("throwing shade");
            throw GammaError.toss(pipeReadError, c.getName(), e.getMessage());
        }
    }

    /**
     *
     * @return next bit map from read end; null if end of pipe reached
     */
    public BMap getNextBMap() {
        try {
            String serializedbmap = b.readLine();
            if (serializedbmap == null) {
                return null;
            }
            BMap bm = BMap.unserialize(serializedbmap);
            return bm;
        } catch (IOException e) {
            throw GammaError.toss(pipeReadError, c.getName(), e.getMessage());
        }
    }

    /**
     * read a serialized bitmap from the readEnd
     *
     * @return -- string of bitmap
     * @throws Exception -- IO exception most likely
     */
    public String getSerializedMap() throws Exception {
        return b.readLine();
    }

    /** 
     * @return name of this connector 
     */
    public String getName() {
        return c.getName();
    }

}
