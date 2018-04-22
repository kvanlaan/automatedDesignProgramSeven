package gamma;

import gammaSupport.*;
/** HJRefine circuit : this class implements HJOIN as the
 * BLOOM, BFILTER, HJOIN circuit -- the first refinement of Gamma */
public class HJRefine implements gammaSupport.GammaConstants {
    
    private final Connector inA, inB, out, bloom_to_hjoin, bloom_to_bfilter, bfilter_to_hjoin;
    private final String hashKey;
    public HJRefine(Connector inA, Connector inB, Connector out, String hashKey){
        this.inA = inA;
        this.inB = inB;
        this.out = out;
        this.hashKey = hashKey;
        
        bloom_to_hjoin = new Connector("bloom to hjoin");
        bloom_to_bfilter = new Connector("bloom to bfilter");
        bfilter_to_hjoin = new Connector("bfilter to hjoin");
        
        bloom_to_hjoin.setTableSchema(inA.getTableSchema());
        bloom_to_bfilter.setTableSchema(inA.getTableSchema());
        bfilter_to_hjoin.setTableSchema(inB.getTableSchema());
        out.setTableSchema(inA.getTableSchema());
       
        System.out.println("Henlo I am born");
    }
    
    
    public void run(){
        System.out.println("Running so refined-like");
        Bloom bloom = new Bloom(inA, bloom_to_hjoin, bloom_to_bfilter, hashKey);
        bloom.run();
        System.out.println("Henlo I am bloomed");
        BFilter filter = new BFilter(inB, bloom_to_bfilter, bfilter_to_hjoin, hashKey);
        filter.run();
        System.out.println("Henlo I am filtered");
////        HJoin join = new HJoin(bloom_to_hjoin, bfilter_to_hjoin, out, hashKey);
//        join.run();
        System.out.println("Henlo I am refined");
    }
}
