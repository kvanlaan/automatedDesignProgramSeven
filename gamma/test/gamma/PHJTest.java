package gamma;

import gammaSupport.Connector;
import gammaSupport.*;
import org.junit.Test;

public class PHJTest extends JoinCommon {

    public PHJTest() {
    }

    @Test
    public void testHJRefine() throws Exception {
        jointest();
    }

    static int splitLen = 4;

    public Connector[] initConnectorArray(String name) {
        Connector[] result =  new Connector[splitLen];
        for (int i=0; i<splitLen; i++) result[i] = new Connector(name+"_"+i);
        return result;
    }

    public void join(String r1name, String r2name, String jk1, String jk2) throws Exception {
        
        ThreadList.init();
        Connector i1 = new Connector("input1");
        Connector i2 = new Connector("input2");
        Connector o   =  new Connector("output");

        new ReadTable(i1, GammaConstants.Rel+r1name);
        new ReadTable(i2, GammaConstants.Rel+r2name);
        new PHJ(i1, i2, o, jk1, jk2);
        Print p = new Print(o);
        ThreadList.run(p);
        System.out.format("\n\n");
    }

}