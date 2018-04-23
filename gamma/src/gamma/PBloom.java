package gamma;

import gammaSupport.Connector;

/** parallel Bloom circuit -- this is the map-reduce Bloom circuit */
public class PBloom implements gammaSupport.GammaConstants {
    public PBloom(Connector in, Connector out, Connector outm, String joinkey) {
        Connector[] hsplit_to_bloom = Connector.newConnectorArray("hsplit to bloom");
        Connector[] bloom_to_merge = Connector.newConnectorArray("bloom to merge");
        Connector[] bloom_to_mmerge = Connector.newConnectorArray("bloom to mmerge");

        new HSplit(in, hsplit_to_bloom, joinkey);
        for (int i = 0; i < splitLen; i ++) {
            new Bloom(hsplit_to_bloom[i], bloom_to_merge[i], bloom_to_mmerge[i], joinkey);
        }
        new Merge(bloom_to_merge, out);
        new MergeM(bloom_to_mmerge, outm);
    }
}
