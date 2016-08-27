package pl.com.bottega.encryption;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by anna on 27.08.2016.
 */
public class CesarInputStream extends InputStream{

    private InputStream inputStream;
    private int key;

    public CesarInputStream(InputStream outputStream, int key) {
        this.inputStream = outputStream;
        this.key = key;
    }

    @Override
    public int read() throws IOException {
        int byteRead = inputStream.read();
        if (byteRead == -1)
            return -1;
        return byteRead - key;
    }
}
