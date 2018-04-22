package gamma;

import gammaSupport.Connector;

/** parallel sort circuit */
public class PSort implements gammaSupport.GammaConstants {
    Merge  merge;

    /**
     * parallel, map-reduce sort constructor
     * @param in -- input connector
     * @param out -- output connector
     * @param sortkey -- key on which to sort; must be a field of the input connector
     */
    public PSort( Connector in, Connector out, String sortkey) {        
        Connector[] to = Connector.newConnectorArray("s");
        Connector[] from = Connector.newConnectorArray("r");

        new HSplit(in, to, sortkey);
        for (int i=0; i<splitLen; i++) new Sort( to[i], from[i], sortkey );
        new OMerge(from,out,sortkey);
    }
}
