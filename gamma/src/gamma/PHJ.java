package gamma;

import gammaSupport.Connector;
import static gammaSupport.GammaConstants.splitLen;

/** parallel HashJoin circuit -- this is the map-reduce of a single HJOIN 
 *  box circuit */
public class PHJ implements gammaSupport.GammaConstants {
    public PHJ(Connector inA, Connector inB, Connector out, String hashKeyA, String hashKeyB) {
        Connector[] hsplitA_to_hjoin = Connector.newConnectorArray("hsplitA to hjoin");
        Connector[] hsplitB_to_hjoin = Connector.newConnectorArray("hsplitB to hjoin");
        Connector[] hjoin_to_merge = Connector.newConnectorArray("hjoin to merge");
        
        new HSplit(inA, hsplitA_to_hjoin, hashKeyA);
        new HSplit(inB, hsplitB_to_hjoin, hashKeyB);
        for (int i = 0; i < splitLen; i ++) {
            new HJoin(hsplitA_to_hjoin[i], hsplitB_to_hjoin[i], hjoin_to_merge[i], hashKeyA,hashKeyB);
        }
        new Merge(hjoin_to_merge, out);
    }
}
