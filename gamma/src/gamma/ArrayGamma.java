package gamma;

import gammaSupport.Connector;

/** Gamma HJOIN implementation lifted to arrays */
public class ArrayGamma implements gammaSupport.GammaConstants {
    // only graduate class has to implement this
    
    public ArrayGamma(Connector[] a, Connector[] b, Connector[] out, String hashkeyA, String hashkeyB) {       
//        Connector[] bloomA_to_hjoin = Connector.newConnectorArray("bloomA to hjoin");
//        Connector[] bloom_to_bfilter = Connector.newConnectorArray("bloom to bfilter");
//        Connector[] bfilterB_to_hjoin = Connector.newConnectorArray("bfilterB to hjoin");
//        
//        for (int i = 0; i < splitLen; i ++) {
//            new Bloom(a[i], bloomA_to_hjoin[i], bloom_to_bfilter[i], hashkeyA);
//            new BFilter(b[i], bloom_to_bfilter[i], bfilterB_to_hjoin[i], hashkeyB);
//            new HJoin(bloomA_to_hjoin[i], bfilterB_to_hjoin[i], out[i], hashkeyA, hashkeyB);
//        }
        
        for (int i = 0; i < splitLen; i ++) {
            new Gamma(a[i], b[i], out[i], hashkeyA, hashkeyB);
        }
    }
}
