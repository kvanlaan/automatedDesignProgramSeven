package gamma;

import RegTest.Utility;
import gammaSupport.Connector;
import gammaSupport.*;
import org.junit.Test;

public class PSplitMergeTest implements GammaConstants {

    public PSplitMergeTest() {
    }

    @Test
    public void testSplit() {
        Utility.redirectStdOut("/Outputs/out.txt");
        try {
            splittst("parts.pl", "pno");
            splittst("client.pl","cno");
            splittst("orders.pl", "ono");
        } catch (Exception e) {
            throw new RuntimeException("exception in SplitTest -- split-merge fails");
        }
        Utility.validate("out.txt", "CorrectOutput/SplitTest.txt",true);
    }

    void splittst(String filename, String joinkey) throws Exception {

        Connector in = new Connector("in");
        Connector i[] = Connector.newConnectorArray("i");
        Connector o[] = Connector.newConnectorArray("o");
        Connector out = new Connector("out");

        System.out.println("reading " + filename);
        ThreadList.init();
        new ReadTable(in, GammaConstants.Rel+filename);
        new HSplit(in, i, joinkey);
        for (int j = 0; j < splitLen; j++) {
            new DoNothing(i[j], o[j]);
        }
        new Merge(o, out);
        Print p = new Print(out);
        ThreadList.run(p);
        System.out.println("------------------");
    }
}