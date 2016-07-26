package com.rightminds.biller.entity;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CustomerTest {

    @Test
    public void fromMapShouldReturnCustomerBasedOnMap() throws Exception {
        Map<String, String> map = new HashMap<String, String>() {{
            put("name", "Thiru");
            put("phoneNumber", "9876543210");
            put("address", "perundurai");
        }};

        Customer customer = Customer.fromMap(map);

        assertThat(customer.getPhoneNumber(), is("9876543210"));
        assertThat(customer.getName(), is("Thiru"));
        assertThat(customer.getRewardPoints(), is(0));
        assertThat(customer.getAddress(), is("perundurai"));
    }

    @Test
    public void fromMapShouldReturnCustomerWithRewardPoints() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("name", "Thiru");
            put("phoneNumber", "9876543210");
            put("address", "perundurai");
            put("rewardPoints", 10);
        }};

        Customer customer = Customer.fromMap(map);

        assertThat(customer.getName(), is("Thiru"));
        assertThat(customer.getPhoneNumber(), is("9876543210"));
        assertThat(customer.getAddress(), is("perundurai"));
        assertThat(customer.getRewardPoints(), is(10));
    }
}