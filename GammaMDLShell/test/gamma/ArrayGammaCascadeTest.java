package gamma;

import RegTest.Utility;
import gammaSupport.*;
import org.junit.Test;

public class ArrayGammaCascadeTest {

    public ArrayGammaCascadeTest() {
    }

    @Test
    public void testSplit1() throws Exception {
        Utility.redirectStdOut("out.txt");
        threeWay("odetails.pl", "ono", "orders.pl", "ono", "parts.pl", "pno", "odetails.pno");
        Utility.validate("out.txt", "CorrectOutput/DxOxP.pl",true);
    }
    
    //@Test
    public void testSplit2() throws Exception {
        Utility.redirectStdOut("out.txt");
        threeWay("odetails.pl", "pno", "parts.pl", "pno", "orders.pl", "ono", "odetails.ono");
        Utility.validate("out.txt", "CorrectOutput/DxPxO.pl",true);
    }

    void threeWay(String filename1, String joinkey1, String filename2, String joinkey2, String filename3, 
            String joinkey3, String joinkey12) throws Exception {
        
        ThreadList.init();
        Connector[] in1 = Connector.newConnectorArray("in1");
        Connector[] in2 = Connector.newConnectorArray("in2");
        Connector[] in3 = Connector.newConnectorArray("in3");
        Connector[] out12 = Connector.newConnectorArray("out12");
        Connector[] in12 = Connector.newConnectorArray("in12");
        Connector[] out123 = Connector.newConnectorArray("out123");
        
        new ArrayReadTable(in1, GammaConstants.Rel+filename1, joinkey1);
        new ArrayReadTable(in2, GammaConstants.Rel+filename2, joinkey2);
        new ArrayReadTable(in3, GammaConstants.Rel+filename3, joinkey3);
        
        new ArrayGamma(in1, in2, out12, joinkey1, joinkey2);
        new ArrayCrossBar( out12, in12, joinkey12);
        new ArrayGamma(in12, in3, out123, joinkey12, joinkey3);
        ArrayPrint ap = new ArrayPrint(out123);
        
        ThreadList.run(ap.getPrint());
        System.out.println("------------------");
    }
}
