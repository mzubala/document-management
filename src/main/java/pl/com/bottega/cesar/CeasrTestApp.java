package pl.com.bottega.cesar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by maciuch on 27.08.16.
 */
public class CeasrTestApp {

    public static void main(String[] args) throws Exception {
        OutputStream os = new FileOutputStream("/home/maciuch/tmp/topsecret.txt");
        int k = 255;
        os = new CesarOutputStream(os, k);
        os.write("Ala ma psa".getBytes());
        os.close();

        InputStream is = new FileInputStream("/home/maciuch/tmp/topsecret.txt");
        is = new CesarInputStream(is, k);
        int b;
        StringBuilder sb = new StringBuilder();
        while((b = is.read()) != -1) {
            sb.append((char) b);
        }
        System.out.println(sb.toString());
    }

}
