package pl.com.bottega.documentmanagement.domain;

import java.math.BigDecimal;

/**
 * Created by maciuch on 20.08.16.
 */
public interface PrintCostCalculator {

    BigDecimal cost(int pagesCount);

}
