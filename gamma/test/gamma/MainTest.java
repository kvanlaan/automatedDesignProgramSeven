/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamma;

import org.junit.Test;
import gammaSupport.Connector;
import gammaSupport.ThreadList;

/**
 *
 * @author dsb
 */
public class MainTest {
    
    public MainTest() {
        RegTest.Utility.init();
    }

    @Test
    public void test1() throws Exception {
        RegTest.Utility.redirectStdOut("out.txt");
        // read --> join --> print
        ThreadList.init();
        Connector c1 = new Connector("input1");
        ReadRelation r1 = new ReadRelation("RelationData/client.pl", c1);
        Connector c2 = new Connector("input2");
        ReadRelation r2 = new ReadRelation("RelationData/viewing.pl", c2);
        Connector o = new Connector("output");
//        HJoin hj = new HJoin(c1, c2, jk1, jk2, o);
//        Print p = new Print(o);
//        ThreadList.run(p);
    }    
}
