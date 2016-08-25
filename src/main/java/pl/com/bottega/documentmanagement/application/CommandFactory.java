package pl.com.bottega.documentmanagement.application;

/**
 * Created by Dell on 2016-08-21.
 */
public interface CommandFactory {

    Command createCommand(String command);
}
