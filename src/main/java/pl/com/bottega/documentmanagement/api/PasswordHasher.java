package pl.com.bottega.documentmanagement.api;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

/**
 * Created by Wojciech Winiarski on 31.07.2016.
 */
@Service
public class PasswordHasher {
    public String hashedPassword(String password) {
        return Hashing.sha1().hashString(password, Charsets.UTF_8).toString();
    }
}
