package pl.com.bottega.documentmanagement.domain.cesardecorator;

import java.io.*;

/**
 * Created by Dell on 2016-08-27.
 */
public class CesarApp {
    public static void main(String[] args) throws IOException {
        int k = 3;

        OutputStream outputStream = new FileOutputStream("src/tmp/code.csv");
        outputStream = new CesarFileOutputStream(k, outputStream);
        outputStream.write("Ala ma kota".getBytes());
        outputStream.close();

        InputStream inputStream = new FileInputStream("src/tmp/code.csv");
        inputStream = new CesarFileInputStream(k, inputStream);

        int currentByte = 0;
        StringBuilder sB = new StringBuilder();
        while((currentByte = inputStream.read()) != -1)
            sB.append((char) currentByte);
        System.out.print(sB.toString());

        inputStream.close();

    }
}
