package gamma;

import gammaSupport.Connector;

/** an array of DoNothing boxes -- useful for testing */
public class ArrayDoNothing implements gammaSupport.GammaConstants {
    // only graduate class has to implement this
    public ArrayDoNothing(Connector[] in, Connector[] out) {
        for (int i = 0; i < splitLen; i ++) {
            new DoNothing(in[i], out[i]);
        }
    }
}
