package com.rightminds.biller.entity;

import com.rightminds.biller.model.BillStatus;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BillTest {

    @Test
    public void fromMapShouldReturnOrderObject() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "order 1");
        map.put("servicecharge", "1");
        map.put("servicetax", "2");
        map.put("subtotal", "10");
        map.put("discount", "5");
        map.put("total", "110");
        map.put("cash", "50");
        map.put("card", "60");
        map.put("status", "IN_PROGRESS");
        HashMap<String, String> customer = new HashMap<String, String>() {{
            put("id", "1");
        }};
        map.put("customer", customer);

        Bill bill = Bill.fromMap(map);

        assertThat(bill.getName(), is("order 1"));
        assertThat(bill.getServiceCharge(), is(new BigDecimal(1)));
        assertThat(bill.getServiceTax(), is(new BigDecimal(2)));
        assertThat(bill.getSubTotal(), is(new BigDecimal(10)));
        assertThat(bill.getDiscount(), is(new BigDecimal(5)));
        assertThat(bill.getTotal(), is(new BigDecimal(110)));
        assertThat(bill.getCash(), is(new BigDecimal(50)));
        assertThat(bill.getCard(), is(new BigDecimal(60)));
        assertThat(bill.getStatus(), is(BillStatus.IN_PROGRESS));
        assertThat(bill.getCustomer(), is(Customer.fromMap(customer)));

    }
}