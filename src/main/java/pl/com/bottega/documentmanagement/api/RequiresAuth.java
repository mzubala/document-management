package pl.com.bottega.documentmanagement.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Dell on 2016-07-09.
 */
//@Retention(RetentionPolicy.RUNTIME) //konieczna adnotacja do stworzenia klasy
//@Target({ElementType.METHOD, ElementType.TYPE}) //opcjonalne (gdzie dostępne)
public @interface RequiresAuth {
}
