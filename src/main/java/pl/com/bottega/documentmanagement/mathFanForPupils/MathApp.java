package pl.com.bottega.documentmanagement.mathFanForPupils;

import com.google.common.collect.Lists;

import java.util.Collection;

/**
 * Created by bartosz.paszkowski on 26.08.2016.
 */
public class MathApp extends MathFanForPupilsConApp {

    public MathApp() {
    }

    @Override
    protected Collection<String> menu() {
        return Lists.newArrayList(
                "Wybierz",
                "1. Rozwiąż równanie kwadratowe",
                "2. Oblicz sinus",
                "3. Oblicz cosinus",
                "4. Oblicz 2 do potęgi",
                "quit - aby wyjść");
    }

    @Override
    protected RequestFactory requestFactory() {
        return new ManagerRequestFactory();
    }


}
