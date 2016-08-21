package pl.com.bottega.documentmanagement.application;

/**
 * Created by maciuch on 21.08.16.
 */
public interface CommandFactory {

    Command createCommand(String command);

}
