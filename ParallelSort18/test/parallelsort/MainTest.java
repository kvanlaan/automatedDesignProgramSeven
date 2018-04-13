/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelsort;

import org.junit.Test;

/**
 *
 * @author dsb
 */
public class MainTest {
    
    public MainTest() {
        RegTest.Utility.init();
    }

    @Test
    public void test1() throws Exception {
        RegTest.Utility.redirectStdOut("out.txt");
        // read --> sort --> print
        Connector read_sort = new Connector();
        Connector sort_print = new Connector();
        Reader r = new Reader("TestData/words.txt", read_sort.out);
        Sorter s = new Sorter(read_sort.in, sort_print.out);
        Printer p = new Printer(sort_print.in);
        r.start();
        s.start();
        p.start();
        p.join();
        RegTest.Utility.validate("out.txt", "Correct/words.txt", false);
    }

    @Test
    public void test3() throws Exception {
        RegTest.Utility.redirectStdOut("out.txt");
        // read-->hash--0-->sort0------merge-->print
        //            --1-->sort1-----|
        //            --2-->sort2-----|
        //            --3-->sort3-----|
        Connector read_hash = new Connector();
        Connector hash_sort0 = new Connector();
        Connector hash_sort1 = new Connector();
        Connector hash_sort2 = new Connector();
        Connector hash_sort3 = new Connector();
        Connector sort0_merge = new Connector();
        Connector sort1_merge = new Connector();
        Connector sort2_merge = new Connector();
        Connector sort3_merge = new Connector();
        Connector merge_print = new Connector();

        Reader r = new Reader("TestData/words.txt", read_hash.out);
        // Printer p = new Printer(read_hash.in);
        HashSplit h = new HashSplit(read_hash.in, hash_sort0.out, hash_sort1.out,
                hash_sort2.out, hash_sort3.out);
        Sorter s0 = new Sorter( hash_sort0.in, sort0_merge.out);
        Sorter s1 = new Sorter( hash_sort1.in, sort1_merge.out);
        Sorter s2 = new Sorter( hash_sort2.in, sort2_merge.out);
        Sorter s3 = new Sorter( hash_sort3.in, sort3_merge.out);
        Merger m = new Merger( sort0_merge.in, sort1_merge.in,
                sort2_merge.in, sort3_merge.in, merge_print.out);
        Printer p = new Printer( merge_print.in);
        r.start();
        h.start();
        s0.start();
        s1.start();
        s2.start();
        s3.start();
        m.start();
        p.start();
        p.join();
        RegTest.Utility.validate("out.txt", "Correct/words.txt", false);
    }
    
}
