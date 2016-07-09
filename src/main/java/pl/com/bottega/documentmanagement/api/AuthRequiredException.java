package pl.com.bottega.documentmanagement.api;

/**
 * Created by Wojciech Winiarski on 09.07.2016.
 */
public class AuthRequiredException extends RuntimeException {

   public   AuthRequiredException(){

       super("Authentication required but no user in current session");
   }
}
