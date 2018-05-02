package gamma;

import gammaSupport.Connector;
import gammaSupport.*;
import org.junit.Test;


public class PHJRefineTest extends JoinCommon implements GammaConstants {

    public PHJRefineTest() {
    }


    @Test
    public void testHJRefine() throws Exception {
        jointest();
    }


    public void join(String r1name, String r2name, String jk1, String jk2) throws Exception {
        ThreadList.init();
        Connector c1 = new Connector("input1");
        Connector c2 = new Connector("input2");
        Connector o = new Connector("output");
        
        new ReadTable(c1, Rel+r1name);
        new ReadTable(c2, Rel+r2name);
        new PHJRefine(c1, c2, o, jk1, jk2);
        Print p = new Print(o);
        ThreadList.run(p);
        System.out.format("\n\n");
    }

}