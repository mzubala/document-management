package pl.com.bottega.documentmanagement.cesar;

import java.io.*;

/**
 * Created by bartosz.paszkowski on 27.08.2016.
 */
public class CesareConsoleApp {

    public static void main(String[] args) throws IOException {
        int key = 10;
        OutputStream o = new FileOutputStream("C:\\Users\\bartosz.paszkowski\\Documents\\topSecretMessage.txt");
        o = new CesarOutputStream(o,key);
        o.write("Ala ma kota".getBytes());
        o.close();

        InputStream is = new FileInputStream("C:\\Users\\bartosz.paszkowski\\Documents\\topSecretMessage.txt");
        is = new CesarInputStream(is,key);
        StringBuilder builder = new StringBuilder();
        int result;
        while ((result = is.read()) != -1){
            char ch = (char) result;
            builder = builder.append(ch);
        }
        System.out.println(builder.toString());


        


    }

}
