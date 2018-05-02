package gamma;

import gammaSupport.*;
/** HJRefine circuit : this class implements PHJ as the
 * PBloom, PBFilter, PHJ circuit -- the first refinement of Gamma */
public class PHJRefine implements gammaSupport.GammaConstants {
    
    private final Connector inA, inB, out, PBloom_to_PHJ, PBloom_to_PBFilter, PBFilter_to_PHJ;
    private final String hashKeyA, hashKeyB;
    
    public PHJRefine(Connector inA, Connector inB, Connector out, String hashKeyA, String hashKeyB){
        this.inA = inA;
        this.inB = inB;
        this.out = out;
        this.hashKeyA = hashKeyA;
        this.hashKeyB = hashKeyB;
        
        PBloom_to_PHJ = new Connector("PBloom to PHJ");
        PBloom_to_PBFilter = new Connector("PBloom to PBFilter");
        PBFilter_to_PHJ = new Connector("PBFilter to PHJ");
        
        PBloom_to_PHJ.setTableSchema(inA.getTableSchema());
        PBloom_to_PBFilter.setTableSchema(inA.getTableSchema());
        PBFilter_to_PHJ.setTableSchema(inB.getTableSchema());
//        out.setTableSchema(inA.getTableSchema());
       
//        System.out.println("Henlo I am born");
        run();
    }
    
    
    private void run(){
//        System.out.println("Running so refined-like");
        PBloom PBloom = new PBloom(inA, PBloom_to_PHJ, PBloom_to_PBFilter, hashKeyA);
//        PBloom.start();
//        System.out.println("Henlo I am PBloomed");
        PBFilter filter = new PBFilter(inB, PBloom_to_PBFilter, PBFilter_to_PHJ, hashKeyB);
//        filter.start();
//        System.out.println("Henlo I am filtered");
        PHJ join = new PHJ(PBloom_to_PHJ, PBFilter_to_PHJ, out, hashKeyA, hashKeyB);
//        join.start();
//        System.out.println("Henlo I am refined");
    }
}
