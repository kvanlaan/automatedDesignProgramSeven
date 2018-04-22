package RegTest;

import PrologDB.DB;
import PrologDB.Table;
import static gammaSupport.GammaConstants.Rel;
import java.io.PrintStream;

/** generates correct answer files for regression tests */
public class getAnswers {

    public static void main(String... args) throws Exception {
        PrintStream out = new PrintStream("out.txt");
        DB cv = DB.readDataBase(Rel + "cv.clientView.pl");
        Table parts = cv.getTable("parts");
        Table odetails = cv.getTable("odetails");
        Table orders = cv.getTable("orders");
        Table viewing = cv.getTable("viewing");
        Table client = cv.getTable("client");

        Table PxD = parts.join("pno", odetails, "pno");
        Table CxV = client.join("cno", viewing, "cno");
        Table OxD = orders.join("ono", odetails, "ono");

        PxD.print(out);
        out.println();
        CxV.print(out);
        out.println();
        OxD.print(out);
        out.close();
        
        out = new PrintStream("PxD.pl");
        PxD.print(out);
        out.close();
        
        out = new PrintStream("CxV.pl");
        CxV.print(out);
        out.close();
        
        out = new PrintStream("OxD.pl");
        OxD.print(out);
        out.close();
        
        Table DxO = odetails.join("ono", orders, "ono");
        Table DxOxP = DxO.join("odetails.pno", parts, "pno");
        out = new PrintStream("DxOxP.pl");
        DxOxP.print(out);
        out.close();
        
        Table DxP = odetails.join("pno", parts, "pno");
        Table DxPxO = DxP.join("odetails.ono", orders, "ono");
        out = new PrintStream("DxPxO.pl");
        DxPxO.print(out);
        out.close();
    }
}

