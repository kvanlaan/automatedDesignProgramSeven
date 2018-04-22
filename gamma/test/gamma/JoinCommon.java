package gamma;

import RegTest.Utility;

public abstract class JoinCommon {

    public JoinCommon() {
    }

    public abstract void join(String r1name, String r2name, String jk1, String jk2) throws Exception;

    public void jointest() throws Exception {
        Utility.redirectStdOut("out.txt");
        join("parts.pl", "odetails.pl", "pno", "pno");
        join("client.pl", "viewing.pl", "cno", "cno");
        join("orders.pl", "odetails.pl", "ono", "ono");
        Utility.validate("out.txt", "CorrectOutput/jointest.pl",true);
    }
    
    public void jointest2( String in1, String in2, String joinkey1, String joinkey2, String answerFile ) throws Exception {
        Utility.redirectStdOut("out.txt");
        join(in1, in2, joinkey1, joinkey2);
        Utility.validate("out.txt", answerFile,true);
    }
}
