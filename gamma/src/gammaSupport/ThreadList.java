package gammaSupport;

import static gammaSupport.GammaDebug.addingThread;
import static gammaSupport.GammaDebug.startingThread;
import static gammaSupport.GammaError.threadException;
import java.util.LinkedList;

/** contains a list of threads finishing the last thread completes the computation */
public class ThreadList implements GammaConstants {

    static LinkedList<Thread> tl = new LinkedList<>();

    /** initialize set of threads to empty */
    public static void init() {
        tl = new LinkedList<>();
    }

    /**
     * add thread to list of threads created
     * @param t -- thread
     */
    public static void add(Thread t) {
        tl.add(t);
        GammaDebug.report(addingThread, t.getClass().getName());
    }

    /** run thread
     * 
     * @param last -- this is the last thread of the bunch
     */
    public static void run(Thread last) {
        try {
            Connector.verifyConnectorSet();
            for (Thread t : tl) {
                t.start();
                GammaDebug.report(startingThread, t.getClass().getName());
            }
            last.join();
        } catch (InterruptedException e) {
            throw GammaError.toss(threadException,e.getMessage());
        }
    }
}
