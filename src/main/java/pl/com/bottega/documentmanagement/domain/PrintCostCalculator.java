package pl.com.bottega.documentmanagement.domain;

import java.math.BigDecimal;

/**
 * Created by bartosz.paszkowski on 20.08.2016.
 */
public interface PrintCostCalculator {

    BigDecimal cost(int pageCount);
}
