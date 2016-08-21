package pl.com.bottega.documentmanagement.infrastructure;

import pl.com.bottega.documentmanagement.domain.PrintingCostCalculator;

import java.math.BigDecimal;

/**
 * Created by Dell on 2016-08-20.
 */
public class GreryPrintingCostCalculator implements PrintingCostCalculator {

    @Override
    public BigDecimal calculate(int pagesCount) {
        return new BigDecimal(pagesCount * 0.05);
    }
}
