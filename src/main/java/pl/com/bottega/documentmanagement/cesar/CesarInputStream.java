package pl.com.bottega.documentmanagement.cesar;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bartosz.paszkowski on 27.08.2016.
 */
public class CesarInputStream extends InputStream {

    private InputStream inputStream;
    private int cesarKey;

    public CesarInputStream(InputStream is, int key) {
        this.inputStream = is;
        this.cesarKey = key;
    }

    @Override
    public int read() throws IOException {
        int dec = inputStream.read();
        if (dec == -1)
            return -1;
        int res = dec-cesarKey;
        return res;
    }
}
