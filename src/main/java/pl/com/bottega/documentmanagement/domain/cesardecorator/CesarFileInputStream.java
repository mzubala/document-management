package pl.com.bottega.documentmanagement.domain.cesardecorator;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Dell on 2016-08-27.
 */
public class CesarFileInputStream extends CesarInputStream {

    public CesarFileInputStream(int cipherKey, InputStream inputStream) {
        super(cipherKey, inputStream);
    }

    @Override
    public int read() throws IOException {
        int readByte = inputStream.read();
        while (readByte != -1)
            return readByte - cipherKey;
        return -1;
    }


}
