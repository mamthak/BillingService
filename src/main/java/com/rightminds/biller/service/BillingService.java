package com.rightminds.biller.service;

import com.rightminds.biller.entity.Order;
import com.rightminds.biller.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

    @Autowired
    private ItemService itemService;

    public void processBill(Order order) {
        BigDecimal serviceCharge = getBigDecimal(configurationService.getByKey(SERVICE_CHARGE).getValue());
        BigDecimal serviceTax = getBigDecimal(configurationService.getByKey(SERVICE_TAX).getValue());
        BigDecimal total = getTotal(order, serviceCharge, serviceTax);

        orderService.save(order.withComputedValues(serviceCharge, serviceTax, total));
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            Integer orderedQuantity = orderItem.getQuantity();
            itemService.reduceInventoryCount(orderItem.getItem(), orderedQuantity);
        }
    }

    private BigDecimal getTotal(Order order, BigDecimal serviceCharge, BigDecimal serviceTax) {
        return order.getSubTotal()
                .add(order.getSubTotal().multiply(serviceCharge.divide(valueOf(100))))
                .add(order.getSubTotal().multiply(serviceTax.divide(valueOf(100))))
                .subtract(order.getDiscount());
    }

}
