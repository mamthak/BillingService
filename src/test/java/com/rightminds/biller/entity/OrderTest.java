package com.rightminds.biller.entity;

import com.rightminds.biller.model.OrderStatus;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderTest {

    @Test
    public void fromMapShouldReturnOrderObject() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "order 1");
        map.put("serviceCharge", "1");
        map.put("serviceTax", "2");
        map.put("subTotal", "10");
        map.put("discount", "5");
        map.put("total", "110");
        map.put("cash", "50");
        map.put("card", "60");
        map.put("status", "IN_PROGRESS");
        HashMap<String, String> customer = new HashMap<String, String>() {{
            put("id", "1");
        }};
        map.put("customer", customer);

        Order order = Order.fromMap(map);

        assertThat(order.getName(), is("order 1"));
        assertThat(order.getServiceCharge(), is(new BigDecimal(1)));
        assertThat(order.getServiceTax(), is(new BigDecimal(2)));
        assertThat(order.getSubTotal(), is(new BigDecimal(10)));
        assertThat(order.getDiscount(), is(new BigDecimal(5)));
        assertThat(order.getTotal(), is(new BigDecimal(110)));
        assertThat(order.getCash(), is(new BigDecimal(50)));
        assertThat(order.getCard(), is(new BigDecimal(60)));
        assertThat(order.getStatus(), is(OrderStatus.IN_PROGRESS));
        assertThat(order.getCustomer(), is(Customer.fromMap(customer)));

    }
}