package RegTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.LinkedList;
import static org.junit.Assert.fail;

public class Utility {

    public static int autopsy = -1;                // line number at which files differ      
    // constants

    private Utility() {
    } // should never be called

    // internal utility methods
    private static void sortFile(String inFileName, String outFileName) {
        BufferedReader br;
        LinkedList<String> lines = new LinkedList<>();
        String line;

        try {
            br = new BufferedReader(new FileReader(inFileName));
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                lines.add(line);
            }
            Collections.sort(lines);
            PrintStream ps = new PrintStream(new File(outFileName));
            for (String myline : lines) {
                if (!ps.equals("")) { // throw away blank lines
                    ps.println(myline);
                }
            }
            ps.close();
        } catch (IOException e) {
            System.err.println("unable to sort " + e.getMessage());
            System.exit(1);
        }
    }

    private static void compareFiles(String fileA, String fileB, String fileMsg) {
        int lineNum = 0;
        String lineA = "", lineB = "";

        try {
            BufferedReader A = new BufferedReader(new FileReader(fileA));
            BufferedReader B = new BufferedReader(new FileReader(fileB));
            while (lineA.equals(lineB)) {
                lineNum++;
                lineA = A.readLine();
                lineB = B.readLine();
                if (lineA == null && lineB == null) {
                    return;
                }
            }
            autopsy = lineNum;
        } catch (IOException e) {
            if (originalErr != null) {
                System.setErr(originalErr);
            }
            System.err.println("Exception in file reading: " + e.getMessage());
        }
        System.err.println();
        System.err.println(">" + lineA);
        System.err.println(">" + lineB);
        fail(fileMsg + " on line " + lineNum);
    }

    // usage: suppose a program outputs files A and B and produces text to StdOut.
    // if this program changes, we want to know if it still produces the correct version of these files
    // and has the same StdOut text.  This can be accomplished by the calls:
    //    regTest.redirectStdOut("stdout.txt");  (place output in file "stdout.txt"
    //    {run program};
    //    regTest.validate("stdout.txt", "correctStdOut.txt", false);  (false means don't sort before comparing files
    //    regTest.validate("A.txt", "correctA.txt", true);   (true means sort both files before comparing)
    //    regTest.validate("B.txt", "correctB.txt", true);
    // sorting before comparison is needed when output ordering may be different and non-consequential
    private static PrintStream originalOut = null;
    private static PrintStream originalErr = null;
    private static PrintStream outfile = null;
    private static PrintStream errfile = null;

    public static void redirectStdOut(String outputFile) {
        originalOut = System.out;
        try {
            outfile = new PrintStream(outputFile);
            System.setOut(outfile);
        } catch (Exception e) {
            System.err.println("RedirectStdOut failed to initialize");
            System.exit(1);
        }
    }

    public static void redirectStdErr(String outputFile) {
        originalErr = System.err;
        try {
            errfile = new PrintStream(outputFile);
            System.setErr(errfile);
        } catch (Exception e) {
            System.err.println("RedirectStdErr failed to initialize");
            System.exit(1);
        }
    }

    private final static String sortedOut = "sortedOut.txt";
    private final static String sortedCorrect = "sortedCor.txt";

    private static void done() {
        if (outfile != null) {
            outfile.close();
            outfile = null;
        }
        if (errfile != null) {
            errfile.close();
            errfile = null;
        }
        if (originalOut != null) {
            System.out.close();
            System.setOut(originalOut);
            originalOut = null;
        }
        if (originalErr != null) {
            System.err.close();
            System.setErr(originalOut);
            originalErr = null;
        }
    }

    public static void validate(String outputFile, String correctFile, boolean sortedTest) {

        String fileMsg = outputFile + " differs from " + correctFile;
        done();
//        if (originalOut!=null) { 
//            
//            System.out.close();
//            System.setOut(originalOut);
//        }
//        if (originalErr!=null){ 
//            System.err.close();
//            System.setErr(originalOut);
//        }

        if (sortedTest) {
            sortFile(outputFile, sortedOut);
            sortFile(correctFile, sortedCorrect);
            outputFile = sortedOut;
            correctFile = sortedCorrect;
            fileMsg = fileMsg + "(compare sortedOut.txt with sortedCor.txt)";
        }
        compareFiles(outputFile, correctFile, fileMsg);
    }

}
