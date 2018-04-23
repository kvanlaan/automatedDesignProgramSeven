package gamma;

import gammaSupport.Connector;

/** merge input array to a single Print */
public class ArrayPrint implements gammaSupport.GammaConstants {
    // only graduate class has to implement this
    Print p;
    public ArrayPrint(Connector[] in) {
        Connector out = new Connector("output");
        new Merge(in, out);
        this.p = new Print(out);
    }
    
    public Print getPrint() {
        return this.p;
    }
}
