package pl.com.bottega.documentmanagement.domain.cesardecorator;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Dell on 2016-08-27.
 */
public class CesarFileOutputStream extends CesarOutputStream {

    public CesarFileOutputStream(int cipherKey, OutputStream outputStream) {
        super(cipherKey, outputStream);
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b + cipherKey);
    }
}
