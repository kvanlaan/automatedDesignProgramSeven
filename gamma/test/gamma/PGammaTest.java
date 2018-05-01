package gamma;

import gammaSupport.Connector;
import gammaSupport.*;
import static gammaSupport.GammaConstants.Rel;
import org.junit.Test;


public class PGammaTest extends JoinCommon implements GammaConstants  {

    public PGammaTest() {
    }

    @Test
    public void testHJRefine() throws Exception {
        jointest();
    }

    @Override
    public void join(String r1name, String r2name, String jk1, String jk2) throws Exception {

        ThreadList.init();
        Connector i1 = new Connector("input1");
        Connector i2 = new Connector("input2");
        Connector o   =  new Connector("output");
        new ReadTable(i1, Rel+r1name);
        new ReadTable(i2, Rel+r2name);
        new PGamma(i1, i2, o, jk1, jk2);
        Print p = new Print(o);
        ThreadList.run(p);
        System.out.format("\n\n");
    }

}