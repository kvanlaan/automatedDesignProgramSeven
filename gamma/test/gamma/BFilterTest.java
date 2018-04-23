package gamma;

import RegTest.Utility;
import gammaSupport.Connector;
import gammaSupport.*;
import org.junit.Test;

public class BFilterTest {

    public BFilterTest() {
    }

    @Test
    public void testExtractBloomFilter() throws Exception {
        Utility.redirectStdOut("out.txt");

        bftst("odetails.pl", "pno", "ftfftffffftttfffffffttffffff");  // filter by parts
        bftst("odetails.pl", "ono", "ffftffftffffffftffffffftffff");  // filter by orders
        bftst("viewing.pl",  "cno", "fftfffffffffffftfffffftfffff");  // filter by client

        Utility.validate("out.txt", "CorrectOutput/BFtest.txt", true);
    }

    void bftst( String filename, String joinkey, String filter) throws Exception {
       ThreadList.init();
       Connector m = new Connector("map");
       Connector in = new Connector("indata");
       Connector out = new Connector("outdata");

       new BloomSimulator(m, filter);
       new ReadTable(in, GammaConstants.Rel + filename);
       new BFilter(in, m, out, joinkey );
       Print p = new Print(out);
       ThreadList.run(p);
       System.out.println("=====================");
    }

}