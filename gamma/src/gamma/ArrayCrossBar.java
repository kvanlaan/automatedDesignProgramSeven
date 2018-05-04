package gamma;

import gammaSupport.Connector;
import gammaSupport.ReadEnd;

/** ArrayCrossBar is an nxn array of constructors that split and then merge */
public class ArrayCrossBar implements gammaSupport.GammaConstants {
    Connector[][] conMatrix;
    Connector[] in12;
    Connector[] out12;
    
    // only graduate class has to implement this
    
    public ArrayCrossBar(Connector[] in12, Connector[] out12, String joinkey12) {
        this.conMatrix = Connector.newConnectorMatrix("out12");
        this.in12 = in12;
        this.out12 = out12;
        
        for (int i = 0; i < splitLen; i ++) {
//            ReadEnd re = in12[i].getReadEnd();
            new HSplit(in12[i], conMatrix[i], joinkey12);
        }
        
        Connector[][] conMatrixTrans = Connector.transpose(conMatrix);
        
        for (int i = 0; i < splitLen; i ++) {
            new Merge(conMatrixTrans[i], out12[i]);
        }
    }    
}
