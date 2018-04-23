package gamma;

import gammaSupport.*;
import PrologDB.*;
/** code for entire Gamma circuit */
public class Gamma implements gammaSupport.GammaConstants {
    
    private final Connector A;
    private final Connector B;
    private final Connector out;
    private final String hashkeyA;
    private final String hashkeyB;
    
    public Gamma(Connector a, Connector b, Connector out, String hashkeyA, String hashkeyB){
        this.A = a;
        this.B = b;
        this.out = out;
        this.hashkeyA = hashkeyA;
        this.hashkeyB = hashkeyB;
        
        run();
    }
    
    public void run(){
        // to do
        Connector[] hsplitA_to_bloom = Connector.newConnectorArray("hsplitA to bloom");
        Connector[] hsplitB_to_bfilter = Connector.newConnectorArray("hsplitB to bfilter");
        Connector[] bloomA_to_hjoin = Connector.newConnectorArray("bloomA to hjoin");
        Connector[] bloom_to_bfilter = Connector.newConnectorArray("bloom to bfilter");
        Connector[] bfilterB_to_hjoin = Connector.newConnectorArray("bfilterB to hjoin");
        Connector[] hjoin_to_merge = Connector.newConnectorArray("hjoin to merge");
        
        new HSplit(A, hsplitA_to_bloom, hashkeyA);
        new HSplit(B, hsplitB_to_bfilter, hashkeyB);
        for (int i = 0; i < splitLen; i ++) {
            new Bloom(hsplitA_to_bloom[i], bloomA_to_hjoin[i], bloom_to_bfilter[i], hashkeyA);
            new BFilter(hsplitB_to_bfilter[i], bloom_to_bfilter[i], bfilterB_to_hjoin[i], hashkeyB);
            new HJoin(bloomA_to_hjoin[i], bfilterB_to_hjoin[i], hjoin_to_merge[i], hashkeyA, hashkeyB);
        }
        new Merge(hjoin_to_merge, out);
    }
    
}
