package pl.com.bottega.documentmanagement.infrastructure;

import pl.com.bottega.documentmanagement.domain.PrintCostCalculator;

import java.math.BigDecimal;

/**
 * Created by bartosz.paszkowski on 20.08.2016.
 */

public class GrayPrintCostCalculator implements PrintCostCalculator {
    @Override
    public BigDecimal cost(int pageCount) {
        return new BigDecimal(pageCount*0.05);
    }
}
