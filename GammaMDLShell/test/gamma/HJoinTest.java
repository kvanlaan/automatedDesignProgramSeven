package gamma;

import gammaSupport.Connector;
import gammaSupport.*;
import org.junit.Test;

public class HJoinTest extends JoinCommon {

    public HJoinTest() {
    }

    @Test
    public void testHashJoin() throws Exception {
        jointest();
    }

    @Test
    public void test1() throws Exception {
        jointest2("parts.pl", "odetails.pl", "pno", "pno", "CorrectOutput/PxD.pl");
    }

    @Test
    public void test2() throws Exception {
        jointest2("client.pl", "viewing.pl", "cno", "cno", "CorrectOutput/CxV.pl");
    }

    @Test
    public void test3() throws Exception {
        jointest2("orders.pl", "odetails.pl", "ono", "ono", "CorrectOutput/OxD.pl");
    }

    @Override
    public void join(String r1name, String r2name, String jk1, String jk2) throws Exception {
        ThreadList.init();
        Connector c1 = new Connector("input1");
        Connector c2 = new Connector("input2");
        Connector o = new Connector("output");
        
        new ReadTable(c1, GammaConstants.Rel + r1name);
        new ReadTable(c2, GammaConstants.Rel + r2name);
        new HJoin(c1, c2, o, jk1, jk2);
        Print p = new Print(o);
        ThreadList.run(p);
        System.out.format("\n\n");
    }
}
