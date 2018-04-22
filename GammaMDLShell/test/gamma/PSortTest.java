package gamma;

import RegTest.Utility;
import gammaSupport.Connector;
import gammaSupport.GammaConstants;
import gammaSupport.ThreadList;
import org.junit.Test;
import static org.junit.Assert.*;

public class PSortTest {

    public PSortTest() {
    }

    public void theWork(String tableFile, String sortKey) {
        try {
            Utility.redirectStdOut("out.txt");

            ThreadList.init();
            Connector to = new Connector("toSort"); 
            Connector from = new Connector("fromSort");
            
            new ReadTable(to, GammaConstants.Rel+tableFile);
            new PSort(to,from,sortKey);
            Print p = new Print(from);
            ThreadList.run(p);

            Utility.validate("out.txt", "CorrectOutput/Sorted"+tableFile,false);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testReadParts() {
        theWork("parts.pl","pname");
    }
    
    @Test
    public void testReadViewing() {
         theWork("viewing.pl","propertyno");
    }

    @Test
    public void testReadOrders() {
         theWork("orders.pl","eno");
    }
    
    @Test
    public void testReadOdetails() {
         theWork("odetails.pl","qty");
    }
    
   @Test
    public void testReadClient() {
         theWork("client.pl","fname");
    }
}
