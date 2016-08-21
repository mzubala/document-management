package pl.com.bottega.documentmanagement.domain;

import java.math.BigDecimal;

/**
 * Created by Dell on 2016-08-20.
 */
public interface PrintingCostCalculator {

    BigDecimal calculate(int pagesCount);
}
