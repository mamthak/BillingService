package com.rightminds.biller.service;

import com.rightminds.biller.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BillingService {

    @Autowired
    private OrderService orderService;

    public void processBill(Order order) {
        BigDecimal total = getTotal(order);

        orderService.save(order.withTotalAndCompletedStatus(total));
    }

    private BigDecimal getTotal(Order order) {
        return order.getSubTotal()
                .add(order.getServiceCharge())
                .add(order.getServiceTax())
                .subtract(order.getDiscount());
    }

}
