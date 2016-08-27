package pl.com.bottega.cesar;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by maciuch on 27.08.16.
 */
public class CesarInputStream extends InputStream {

    private InputStream decorated;
    private int key;

    public CesarInputStream(InputStream decorated, int key) {
        this.decorated = decorated;
        this.key = key;
    }

    @Override
    public int read() throws IOException {
        int b = decorated.read();
        if(b == -1)
            return -1;
        return b - key;
    }
}
