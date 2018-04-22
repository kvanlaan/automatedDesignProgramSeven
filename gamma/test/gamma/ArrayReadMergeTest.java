package gamma;

import RegTest.Utility;
import gammaSupport.*;
import org.junit.Test;

public class ArrayReadMergeTest {

    public ArrayReadMergeTest() {
    }

    @Test
    public void testSplit() {
        Utility.redirectStdOut("out.txt");
        try {
            splittst("parts.pl", "pno");
            splittst("client.pl", "cno");
            splittst("orders.pl", "ono");
        } catch (Exception e) {
            throw new RuntimeException("exception in SplitTest -- split-merge fails" + e.getMessage());
        }
        Utility.validate("out.txt", "CorrectOutput/SplitTest.txt",true);
    }

    void splittst(String filename, String joinkey) throws Exception {

        ThreadList.init();
        System.out.println("reading " + filename);
        Connector[] in = Connector.newConnectorArray("in");
        Connector[] out = Connector.newConnectorArray("out");
        
        new ArrayReadTable(in, GammaConstants.Rel+filename, joinkey);
        new ArrayDoNothing(in,out);
        ArrayPrint ap = new ArrayPrint(out);
        ThreadList.run(ap.getPrint());
        System.out.println("------------------");
    }
}
