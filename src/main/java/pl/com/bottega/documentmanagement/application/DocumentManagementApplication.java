package pl.com.bottega.documentmanagement.application;

import com.google.common.collect.Lists;

import java.util.Collection;

/**
 * Created by Dell on 2016-08-21.
 */
public class DocumentManagementApplication extends ConsoleApplication {

    @Override
    protected void execute(String cmd) {
        System.out.println("Execute command " + cmd);
    }

    @Override
    protected Collection<String> menuItems() {
        return Lists.newArrayList(
                "1. Create document",
                "2. Search documents",
                "3. Edit document",
                "4. Verify document"
        );
    }

    public static void main(String[] args) {
        new DocumentManagementApplication().run();
    }
}
