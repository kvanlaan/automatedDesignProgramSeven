package gamma;

import gammaSupport.Connector;

/** create a single ReadTable and fan it out by an HSplit over joinkey */
public class ArrayReadTable implements gammaSupport.GammaConstants {    
    // only graduate class has to implement this
    public ArrayReadTable(Connector[] out, String filename, String joinkey) {
        Connector in = new Connector("input");
        new ReadTable(in, filename);
        new HSplit(in, out, joinkey);
    }
}
