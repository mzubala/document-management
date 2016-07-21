package pl.com.bottega.documentmanagement.api;

import com.sun.org.apache.regexp.internal.RE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Wojciech Winiarski on 09.07.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
//@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RequiresAuth {
    String[] roles() default {};

}
