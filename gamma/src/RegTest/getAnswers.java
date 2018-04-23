package RegTest;

import PrologDB.DB;
import PrologDB.Table;
import gamma.BFilter;
import gamma.BloomSimulator;
import gamma.DoNothing;
import gamma.HJoin;
import gamma.HSplit;
import gamma.Merge;
import gamma.Print;
import gamma.PrintMap;
import gamma.ReadRelation;
import gamma.ReadTable;
import gammaSupport.Connector;
import gammaSupport.GammaConstants;
import static gammaSupport.GammaConstants.Rel;
import static gammaSupport.GammaConstants.splitLen;
import gammaSupport.ThreadList;
import java.io.PrintStream;

/** generates correct answer files for regression tests */
public class getAnswers {

    public static void main(String... args) throws Exception {
        // test connector & ReadTable
//        RegTest.Utility.redirectStdOut("out.txt");
        // read --> join --> print
//        ThreadList.init();
//        Connector c1 = new Connector("input1");
//        ReadRelation r1 = new ReadRelation("RelationData/client.pl", c1);
//        Print p = new Print(c1);
//        ThreadList.run(p);
//        
        
        // test split -> donothing -> merge
//        String filename = "orders.pl";
//        String joinkey = "ono";
//        
//        Connector in = new Connector("in");
//        Connector i[] = Connector.newConnectorArray("i");
//        Connector o[] = Connector.newConnectorArray("o");
//        Connector out = new Connector("out");
//
//        System.out.println("reading " + filename);
//        ThreadList.init();
//        new ReadTable(in, GammaConstants.Rel+filename);
//        new HSplit(in, i, joinkey);
//        for (int j = 0; j < splitLen; j++) {
//            new DoNothing(i[j], o[j]);
//        }
//        new Merge(o, out);
//        Print p = new Print(out);
//        ThreadList.run(p);
//        



//        // test hjoin
//        String r1name = "parts.pl";
//        String r2name = "odetails.pl";
//        String jk1 = "pno";
//        String jk2 = "pno";
//
//        Connector c1 = new Connector("input1");
//        Connector c2 = new Connector("input2");
//        Connector o = new Connector("output");
//        
//        new ReadTable(c1, GammaConstants.Rel + r1name);
//        new ReadTable(c2, GammaConstants.Rel + r2name);
//        new HJoin(c1, c2, o, jk1, jk2);
//        Print p = new Print(o);
//        ThreadList.run(p);
//        System.out.format("\n\n");



        // test bfilter & bloomsimulator
        String filename = "odetails.pl";
        String joinkey = "pno";
        String filter = "ftfftffffftttfffffffttffffff";        
        
//        ThreadList.init();
        Connector m = new Connector("map");
        Connector in = new Connector("indata");
        Connector out = new Connector("outdata");

        new BloomSimulator(m, filter);
        new ReadTable(in, GammaConstants.Rel + filename);
        new BFilter(in, m, out,joinkey );
        Print p = new Print(out);
//        PrintMap pm = new PrintMap(m);
        ThreadList.run(p);
//        p.join();
//        pm.join();
        
        
        
        
        
//        PrintStream out = new PrintStream("/out.txt");
//        DB cv = DB.readDataBase(Rel + "cv.clientView.pl");
//        Table parts = cv.getTable("parts");
//        Table odetails = cv.getTable("odetails");
//        Table orders = cv.getTable("orders");
//        Table viewing = cv.getTable("viewing");
//        Table client = cv.getTable("client");
//
//        Table PxD = parts.join("pno", odetails, "pno");
//        Table CxV = client.join("cno", viewing, "cno");
//        Table OxD = orders.join("ono", odetails, "ono");
//
//        PxD.print(out);
//        out.println();
//        CxV.print(out);
//        out.println();
//        OxD.print(out);
//        out.close();
//        
//        out = new PrintStream("PxD.pl");
//        PxD.print(out);
//        out.close();
//        
//        out = new PrintStream("CxV.pl");
//        CxV.print(out);
//        out.close();
//        
//        out = new PrintStream("OxD.pl");
//        OxD.print(out);
//        out.close();
//        
//        Table DxO = odetails.join("ono", orders, "ono");
//        Table DxOxP = DxO.join("odetails.pno", parts, "pno");
//        out = new PrintStream("DxOxP.pl");
//        DxOxP.print(out);
//        out.close();
//        
//        Table DxP = odetails.join("pno", parts, "pno");
//        Table DxPxO = DxP.join("odetails.ono", orders, "ono");
//        out = new PrintStream("DxPxO.pl");
//        DxPxO.print(out);
//        out.close();
    }
}

