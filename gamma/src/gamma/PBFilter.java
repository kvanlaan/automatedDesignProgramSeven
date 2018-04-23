package gamma;

import gammaSupport.Connector;

/** parallel BFilter circuit : This is the map reduce of BFilter circuit */
public class PBFilter implements gammaSupport.GammaConstants {
    public PBFilter(Connector in, Connector inm, Connector out, String joinkey) {
        Connector[] msplit_to_bfilter = Connector.newConnectorArray("msplit to bfilter");
        Connector[] hsplit_to_bfilter = Connector.newConnectorArray("hsplit to bfilter");
        Connector[] bfilter_to_merge = Connector.newConnectorArray("bfilter to merge");
        
        new SplitM(inm, msplit_to_bfilter);
        new HSplit(in, hsplit_to_bfilter, joinkey);
        for (int i = 0; i < splitLen; i ++) {
            new BFilter(hsplit_to_bfilter[i], msplit_to_bfilter[i], bfilter_to_merge[i], joinkey);
        }
        new Merge(bfilter_to_merge, out);
    }
}
