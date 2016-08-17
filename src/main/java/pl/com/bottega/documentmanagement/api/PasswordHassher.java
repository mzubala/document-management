package pl.com.bottega.documentmanagement.api;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

/**
 * Created by paulina.pislewicz on 2016-07-31.
 */
@Service
public class PasswordHassher {
    public String hashedPassword(String password) {
        return Hashing.sha1().hashString(password, Charsets.UTF_8).toString();
    }

}
