package gamma;

import RegTest.Utility;
import gammaSupport.Connector;
import gammaSupport.GammaConstants;
import gammaSupport.ThreadList;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReadTableTest {

    public ReadTableTest() {
    }

    public void theWork(String tableFile) {
        try {
            Utility.redirectStdOut("out.txt");

            ThreadList.init();
            Connector c = new Connector("singleton"); 
            ReadTable r = new ReadTable(c, GammaConstants.Rel+tableFile);
            Print p = new Print(c);
            ThreadList.run(p);

            Utility.validate("out.txt", "CorrectOutput/"+tableFile,true);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testReadParts() {
        theWork("parts.pl");
    }
    
    @Test
    public void testReadViewing() {
         theWork("viewing.pl");
    }

    @Test
    public void testReadOrders() {
         theWork("orders.pl");
    }
    
    @Test
    public void testReadOdetails() {
         theWork("odetails.pl");
    }
    
    @Test
    public void testReadClient() {
         theWork("client.pl");
    }
}
