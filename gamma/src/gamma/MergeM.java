package gamma;

import gammaSupport.BMap;
import gammaSupport.Connector;
import gammaSupport.ReadEnd;
import gammaSupport.ThreadList;
import gammaSupport.WriteEnd;

/** Merge bitmap boxes into a single box -- check class notes!!! */
public class MergeM extends Thread implements gammaSupport.GammaConstants {
    ReadEnd[] in;
    WriteEnd out;

    @SuppressWarnings("LeakingThisInConstructor")
    public MergeM(Connector in[], Connector out) {
        this.in = new ReadEnd[splitLen];
        for (int i = 0; i < splitLen; i ++) { 
            this.in[i] = in[i].getReadEnd(); 
        }
        
        this.out = out.getWriteEnd();
        
        ThreadList.add(this);
    }
    
    @Override
    public void run() {
        BMap bm = in[0].getNextBMap();
        for (int i = 1; i < splitLen; i ++) {
            bm = BMap.or(bm, in[i].getNextBMap());
        }
        out.putNextBMap(bm);
        out.close();
    }
}
