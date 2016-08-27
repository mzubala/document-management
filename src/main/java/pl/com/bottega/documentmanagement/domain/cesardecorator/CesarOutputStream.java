package pl.com.bottega.documentmanagement.domain.cesardecorator;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Dell on 2016-08-27.
 */
public abstract class CesarOutputStream extends OutputStream {

    protected int cipherKey;
    protected OutputStream outputStream;

    public CesarOutputStream(int cipherKey, OutputStream outputStream) {
        this.cipherKey = cipherKey;
        this.outputStream = outputStream;
    }

    @Override
    public abstract void write(int b) throws IOException;
}
