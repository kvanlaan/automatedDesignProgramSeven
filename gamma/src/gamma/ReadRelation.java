/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamma;

import java.io.*;
import java.util.*;

/**
 *
 * @author katrinavanlaan
 */
public class ReadRelation {

    public static StringTokenizer readFile(String inputFile) {
        try {
            File file = new File(inputFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            fileReader.close();

            StringTokenizer st = new StringTokenizer(stringBuffer.toString());
            System.out.println("---- Split by space ------");
            while (st.hasMoreElements()) {
                System.out.println(st.nextElement());
            }
            return st;
        } catch (IOException e) {
            e.printStackTrace();

        }
        StringTokenizer st2 = new StringTokenizer("");
        return st2;
    }

}
