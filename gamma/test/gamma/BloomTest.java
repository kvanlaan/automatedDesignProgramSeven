package gamma;

import RegTest.Utility;
import gammaSupport.Connector;
import gammaSupport.*;
import static gammaSupport.GammaConstants.Rel;
import org.junit.Test;


public class BloomTest{

    public BloomTest() {
    }

    @Test
    public void testBloomFilter1()  throws Exception {
        Utility.redirectStdOut("out.txt");
        bloomtst("parts.pl", "pno");
        Utility.validate("out.txt", "CorrectOutput/partsBloom.txt",true);
    }
    
    @Test
    public void testBloomFilter2()  throws Exception {
        Utility.redirectStdOut("out.txt");
        bloomtst("client.pl", "cno");
        Utility.validate("out.txt", "CorrectOutput/clientBloom.txt",true);
    }
    
    @Test
    public void testBloomFilter3()  throws Exception {
        Utility.redirectStdOut("out.txt");
        bloomtst("orders.pl", "ono");
        Utility.validate("out.txt", "CorrectOutput/ordersBloom.txt",true);
    }
    
    @Test
    public void testBloomFilter4()  throws Exception {
        Utility.redirectStdOut("out.txt");
        bloomtst("odetails.pl", "ono");
        Utility.validate("out.txt", "CorrectOutput/odetailsBloom1.txt",true);
    }
    
    
    @Test
    public void testBloomFilter5()  throws Exception {
        Utility.redirectStdOut("out.txt");
        bloomtst("odetails.pl", "pno");
        Utility.validate("out.txt", "CorrectOutput/odetailsBloom2.txt",true);
    }
    
    @Test
    public void testBloomFilter6()  throws Exception {
        Utility.redirectStdOut("out.txt");
        bloomtst("viewing.pl", "cno");
        Utility.validate("out.txt", "CorrectOutput/viewingBloom.txt",true);
    }

    void bloomtst( String filename, String joinkey ) throws Exception  {
        ThreadList.init();
        Connector in = new Connector("input");
        new ReadTable(in, Rel + filename);
        Connector m = new Connector("map");
        Connector out = new Connector("output");
        new Bloom(in, out, m, joinkey);
        PrintMap pm = new PrintMap(m);
        Print p = new Print(out);
        ThreadList.run(p);
        p.join();
        pm.join();
    }

}