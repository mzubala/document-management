package pl.com.bottega.encryption;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by anna on 27.08.2016.
 */
public class CesarOutputStream extends OutputStream{

    private OutputStream outputStream;
    private int key;

    public CesarOutputStream(OutputStream outputStream, int key) {
        this.outputStream = outputStream;
        this.key = key;
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b + key);
    }
}
