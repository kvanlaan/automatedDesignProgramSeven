package gamma;

import RegTest.Utility;
import gammaSupport.*;
import org.junit.Test;


public class CascadeTest {

    public CascadeTest() {
    }

    @Test
    public void testSplit1() throws Exception {
        Utility.redirectStdOut("out.txt");
        threeWay("odetails.pl", "ono", "orders.pl", "ono", "parts.pl", "pno", "odetails.pno");
        Utility.validate("out.txt", "CorrectOutput/DxOxP.pl",true);
    }
    
    @Test
    public void testSplit2() throws Exception {
        Utility.redirectStdOut("out.txt");
        threeWay("odetails.pl", "pno", "parts.pl", "pno", "orders.pl", "ono", "odetails.ono");
        Utility.validate("out.txt", "CorrectOutput/DxPxO.pl",true);
    }
    
    void threeWay(String filename1, String joinkey1, String filename2, String joinkey2, String filename3, 
            String joinkey3, String joinkey12) throws Exception {

        Connector in1 = new Connector("in1");
        Connector in2 = new Connector("in2");
        Connector in3 = new Connector("in3");
        Connector out1 = new Connector("out1");
        Connector out2 = new Connector("out2");

        ThreadList.init();
        new ReadTable(in1, GammaConstants.Rel+filename1);
        new ReadTable(in2, GammaConstants.Rel+filename2);
        new ReadTable(in3, GammaConstants.Rel+filename3);

        new HJoin(in1, in2, out1, joinkey1, joinkey2);
        new HJoin(out1, in3, out2, joinkey12, joinkey3);

        Print p = new Print(out2);
        ThreadList.run(p);
        System.out.println("------------------");
    }
}
