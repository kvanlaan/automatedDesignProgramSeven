package gammaSupport;

import PrologDB.Tuple;
import static gammaSupport.GammaError.pipeWriteError;
import java.io.PrintStream;

/**
 * this is an TupleStream adaptor that maps TupleStream to Java pipes
 */
public class WriteEnd {

    PrintStream out;
    Connector c;

    WriteEnd(Connector c) {
        this.c = c;
        out = c.out;
    }

    /**
     * put next tuple down write end
     *
     * @param t -- tuple to write
     */
    public void putNextTuple(Tuple t) {
        try {
            String e = TupleSupport.serialize(t);
            out.println(e);
        } catch (Exception e) {
            throw GammaError.toss(pipeWriteError, c.getName(), e.getMessage());
        }
    }

    /**
     *
     * @param b - bitmap to transmit
     */
    public void putNextBMap(BMap b) {
        try {
            out.println(b.serialize());
        } catch (Exception e) {
            throw GammaError.toss(pipeWriteError, c.getName(), e.getMessage());
        }
    }

    /**
     * write end close
     */
    public void close() {
        out.close();
    }

    /**
     * @return string name of connector
     */
    public String getName() {
        return c.getName();
    }

}
