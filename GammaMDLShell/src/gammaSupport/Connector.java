package gammaSupport;

import PrologDB.ErrorReport;
import PrologDB.TableSchema;
import static gammaSupport.GammaError.connectorConstructor;
import static gammaSupport.GammaError.tableSchemaNotSet;
import static gammaSupport.GammaError.undefinedField;
import java.io.*;
import java.util.LinkedList;

/** the connector objects to be used in Gamma */
public class Connector {

    // used in VerifyRelationSet
    private static LinkedList<Connector> all = new LinkedList<Connector>();

    /** the input reader */
    public BufferedReader in;
    /** the output writer */
    public PrintStream out;
    /** the table schema for 'this' connector */
    public TableSchema tableSchema;
    /** the name given to this connector */
    public String name;

    // constructors
    private Connector() { /* should never be called */ }

    /** primary constructor
     * @param name -- name of the constructor
     */
    public Connector(String name) {
        try {
            this.name = name;
            PipedOutputStream writeEnd = new PipedOutputStream();
            PipedInputStream readEnd = new PipedInputStream(writeEnd);
            out = new PrintStream(writeEnd);
            in = new BufferedReader(new InputStreamReader(readEnd));
            all.add(this);
        } catch (IOException e) {
            throw GammaError.toss(connectorConstructor, name, e.getMessage());
        }
    }

    /**
     * @return read end of 
     */
    public ReadEnd getReadEnd() {
        return new ReadEnd(this);
    }

    /**
     * @return WriteEnd of a connector
     */
    public WriteEnd getWriteEnd() {
        return new WriteEnd(this);
    }

    /**
     * @return table schema of connector
     */
    public TableSchema getTableSchema() {
        if (tableSchema == null)
          throw GammaError.toss(tableSchemaNotSet,name);    
        return tableSchema;
    }

    /**
     * set table schema of connector
     * @param ts 
     */
    public void setTableSchema(TableSchema ts) {
        this.tableSchema = ts;
    }

    /**
     * constraint --- all connectors must have their TableSchema set before
     * running threads.
     */
    public static void verifyConnectorSet() {
        ErrorReport er = new ErrorReport();
        all.stream()
           .filter(c->c.isTableSchemaUnSet())
           .forEach(c-> er.add(GammaError.EString(tableSchemaNotSet,c.name)));
        er.printReport(System.err);
    }

    /**
     * every connector must be associated with some relation (to type 
     * the tuples that flow along it)
     * @return true if tableSchema ==null
     */
    private boolean isTableSchemaUnSet() { 
        return tableSchema == null;
    }

    /**
     * is field a column of the tableschema of this connector?
     * @param field -- column name
     * @param box -- name of calling box
     */
    public void verifyField(String field, String box) {
        if (tableSchema == null) 
            throw GammaError.toss(tableSchemaNotSet, name);
        if (tableSchema.getColumn(field) == null)
            throw GammaError.toss(undefinedField, field, box, name);
    }
    /**
     * @return name of connector
     */
    public String getName() { return name; }
    
    
        /** factory to construct a connector[]
     * @param name -- name of array
     * @return connector array
     */
    public static Connector[] newConnectorArray(String name) {
        Connector[] result = new Connector[GammaConstants.splitLen];
        for (int i = 0; i < GammaConstants.splitLen; i++) {
            result[i] = new Connector(name + "_" + i);
        }
        return result;
    }


    /** factory to construct a connector[][]
     * @param name -- name of matrix
     * @return connector matrix
     */
    public static Connector[][] newConnectorMatrix(String name) {
        Connector[][] result = new Connector[GammaConstants.splitLen][GammaConstants.splitLen];
        for (int i = 0; i < GammaConstants.splitLen; i++) {
            for (int j = 0; j < GammaConstants.splitLen; j++) {
                result[i][j] = new Connector(name + "_" + i + "_" + j);
            }
        }
        return result;
    }

    /** transposes matrix of connectors -- used in cascading joins
     * @param matrix -- connector matrix to transpose
     * @return transpose of matrix
     */
    public static Connector[][] transpose(Connector[][] matrix) {
        int i;
        int j;
        Connector[][] xirtam = new Connector[GammaConstants.splitLen][GammaConstants.splitLen];
        for (i = 0; i < GammaConstants.splitLen; i++) {
            for (j = 0; j < GammaConstants.splitLen; j++) {
                xirtam[j][i] = matrix[i][j];
            }
        }
        return xirtam;
    }
}
