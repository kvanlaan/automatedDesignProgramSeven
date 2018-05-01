package gamma;

import gammaSupport.*;
import PrologDB.*;
/** code for entire Gamma circuit */
public class PGamma implements gammaSupport.GammaConstants {
    
    private final Connector A;
    private final Connector B;
    private final Connector out;
    private final String hashkeyA;
    private final String hashkeyB;
    
    public PGamma(Connector a, Connector b, Connector out, String hashkeyA, String hashkeyB){
        this.A = a;
        this.B = b;
        this.out = out;
        this.hashkeyA = hashkeyA;
        this.hashkeyB = hashkeyB;
        
        run();
    }
    
    public void run(){
        // to do
        Connector[] hsplitA_to_PBloom = Connector.newConnectorArray("hsplitA to PBloom");
        Connector[] hsplitB_to_PBFilter = Connector.newConnectorArray("hsplitB to PBFilter");
        Connector[] PBloomA_to_PHJ = Connector.newConnectorArray("PBloomA to PHJ");
        Connector[] PBloom_to_PBFilter = Connector.newConnectorArray("PBloom to PBFilter");
        Connector[] PBFilterB_to_PHJ = Connector.newConnectorArray("PBFilterB to PHJ");
        Connector[] PHJ_to_merge = Connector.newConnectorArray("PHJ to merge");
        
        new HSplit(A, hsplitA_to_PBloom, hashkeyA);
        new HSplit(B, hsplitB_to_PBFilter, hashkeyB);
        for (int i = 0; i < splitLen; i ++) {
            new PBloom(hsplitA_to_PBloom[i], PBloomA_to_PHJ[i], PBloom_to_PBFilter[i], hashkeyA);
            new PBFilter(hsplitB_to_PBFilter[i], PBloom_to_PBFilter[i], PBFilterB_to_PHJ[i], hashkeyB);
            new PHJ(PBloomA_to_PHJ[i], PBFilterB_to_PHJ[i], PHJ_to_merge[i], hashkeyA, hashkeyB);
        }
        new Merge(PHJ_to_merge, out);
    }
    
}
