package pl.com.bottega.cesar;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by maciuch on 27.08.16.
 */
public class CesarOutputStream extends OutputStream {

    private OutputStream decorated;
    private int key;

    public CesarOutputStream(OutputStream decorated, int key) {
        this.decorated = decorated;
        this.key = key;
    }

    @Override
    public void write(int b) throws IOException {
        int bCiphered = b + key;
        decorated.write(bCiphered);
    }
}
