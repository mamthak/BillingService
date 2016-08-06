package com.rightminds.biller.service;

import com.rightminds.biller.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.rightminds.biller.AllConstants.SERVICE_CHARGE;
import static com.rightminds.biller.AllConstants.SERVICE_TAX;
import static com.rightminds.biller.util.CastUtil.getBigDecimal;
import static java.math.BigDecimal.valueOf;

@Service
public class BillingService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ConfigurationService configurationService;

    public void processBill(Order order) {
        BigDecimal serviceCharge = getBigDecimal(configurationService.getByKey(SERVICE_CHARGE).getValue());
        BigDecimal serviceTax = getBigDecimal(configurationService.getByKey(SERVICE_TAX).getValue());
        BigDecimal total = getTotal(order, serviceCharge, serviceTax);

        orderService.save(order.withComputedValues(serviceCharge, serviceTax, total));
    }

    private BigDecimal getTotal(Order order, BigDecimal serviceCharge, BigDecimal serviceTax) {
        return order.getSubTotal()
                .add(order.getSubTotal().multiply(serviceCharge.divide(valueOf(100))))
                .add(order.getSubTotal().multiply(serviceTax.divide(valueOf(100))))
                .subtract(order.getDiscount());
    }

}
