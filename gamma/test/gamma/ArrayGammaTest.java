package gamma;

import gammaSupport.Connector;
import gammaSupport.*;
import org.junit.Test;


public class ArrayGammaTest extends JoinCommon implements GammaConstants  {

    public ArrayGammaTest() {
    }

    @Test
    public void test() throws Exception {
        jointest();
    }

    @Override
    public void join(String r1name, String r2name, String jk1, String jk2) throws Exception {
        ThreadList.init();
        Connector[] in1 = Connector.newConnectorArray("in1");
        Connector[] in2 = Connector.newConnectorArray("in2");
        Connector[] out = Connector.newConnectorArray("out");
        
        
        new ArrayReadTable(in1, GammaConstants.Rel+r1name,jk1);
        new ArrayReadTable(in2, GammaConstants.Rel+r2name,jk2);
        new ArrayGamma(in1, in2, out, jk1, jk2);
        ArrayPrint ap = new ArrayPrint(out);
        ThreadList.run(ap.getPrint());
        System.out.format("\n\n");
    }

}