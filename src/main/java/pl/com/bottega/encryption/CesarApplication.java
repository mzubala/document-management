package pl.com.bottega.encryption;

import java.io.*;
import java.io.FileOutputStream;

/**
 * Created by anna on 27.08.2016.
 */
public class CesarApplication {

    public static void main(String[] args) throws Exception{

        try {
            OutputStream os = new FileOutputStream("/Users/anna/Documents/topsecret.txt");
            int k = 5;
            os = new CesarOutputStream(os, k);
            os.write("Ala ma kota".getBytes());
            os.close();

            InputStream is = new FileInputStream("/Users/anna/Documents/topsecret.txt");
            is = new CesarInputStream(is, k);
            int b;
            StringBuilder sb = new StringBuilder();
            while ((b = is.read()) != - 1) {
                sb.append((char) b);
            }
            System.out.println(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
