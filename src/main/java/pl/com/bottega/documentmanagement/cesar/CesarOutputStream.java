package pl.com.bottega.documentmanagement.cesar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by bartosz.paszkowski on 27.08.2016.
 */
public class CesarOutputStream extends OutputStream {


    private  OutputStream decorated;
    private int cesarKey;

    public CesarOutputStream(OutputStream decorated, int cesarKey) throws FileNotFoundException {
        this.decorated = decorated;
        this.cesarKey = cesarKey;

    }

    @Override
    public void write(int b) throws IOException {
        int bCiphered = b + cesarKey;
         decorated.write(bCiphered);
    }
}
