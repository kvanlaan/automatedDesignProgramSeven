package gamma;

import gammaSupport.*;
import PrologDB.*;
/** code for entire Gamma circuit */
public class Gamma implements gammaSupport.GammaConstants {
    
    private final Connector A;
    private final Connector B;
    private final Connector from;
    private final String hashkey;
    
    public Gamma(Connector a, Connector b, Connector from, String hashkey){
        this.A = a;
        this.B = b;
        this.from = from;
        this.hashkey = hashkey;
    }
    
    public void run(){
        
        // to do
        
        
    }
    
}
