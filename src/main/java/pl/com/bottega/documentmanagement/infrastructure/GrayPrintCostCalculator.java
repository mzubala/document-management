package pl.com.bottega.documentmanagement.infrastructure;

import pl.com.bottega.documentmanagement.domain.PrintCostCalculator;

import java.math.BigDecimal;

/**
 * Created by maciuch on 20.08.16.
 */
public class GrayPrintCostCalculator implements PrintCostCalculator {

    @Override
    public BigDecimal cost(int pagesCount) {
        return new BigDecimal(pagesCount * 0.05);
    }

}
