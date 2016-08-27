package pl.com.bottega.documentmanagement.domain.cesardecorator;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Dell on 2016-08-27.
 */
public abstract class CesarInputStream extends InputStream {

    protected int cipherKey;
    protected InputStream inputStream;

    public CesarInputStream(int cipherKey, InputStream inputStream) {
        this.cipherKey = cipherKey;
        this.inputStream = inputStream;
    }

    @Override
    public abstract int read() throws IOException;
}
