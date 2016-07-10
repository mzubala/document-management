package pl.com.bottega.documentmanagement.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by bartosz.paszkowski on 09.07.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
//@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RequiresAuth {

}
