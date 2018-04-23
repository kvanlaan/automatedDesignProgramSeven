package gamma;

import gammaSupport.BMap;
import gammaSupport.Connector;
import gammaSupport.ReadEnd;
import gammaSupport.ThreadList;
import gammaSupport.WriteEnd;

/** split bit map  -- see class notes! */
public class SplitM extends Thread implements gammaSupport.GammaConstants {
    ReadEnd in;
    WriteEnd[] out;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public SplitM(Connector in, Connector[] out) {
        this.in = in.getReadEnd();
        this.out = new WriteEnd[splitLen];
        
        for (int i = 0; i < splitLen; i ++) { 
            this.out[i] = out[i].getWriteEnd(); 
        }

        ThreadList.add(this);
    }
    
    @Override
    public void run() {
        try {
            BMap bm = new BMap(in.getSerializedMap());
            in.close();
            for (int i = 0; i < splitLen; i ++) {
                out[i].putNextBMap(bm.extract(i)); 
                out[i].close();
            }
        } catch (Exception e) {
            System.out.println("ERROR! readEnd getSerializedMap failed!");
        }
    }
}
