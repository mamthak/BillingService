package com.rightminds.biller.entity;

import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ConfigurationTest {

    @Test
    public void fromMapShouldCreateConfigurationFromMap() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", "serviceCharge");
        map.put("value", "10");

        Configuration configuration = Configuration.fromMap(map);

        assertNull(configuration.getId());
        assertThat(configuration.getKey(), is("serviceCharge"));
        assertThat(configuration.getValue(), is("10"));
    }

    @Test
    public void fromMapShouldCreateConfigurationWithIdFromMap() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", "1");
        map.put("key", "serviceCharge");
        map.put("value", "10");
        map.put("createdOn", "1469557404358");

        Configuration configuration = Configuration.fromMap(map);

        assertThat(configuration.getId(), is(1));
        assertThat(configuration.getKey(), is("serviceCharge"));
        assertThat(configuration.getValue(), is("10"));
        assertThat(configuration.getCreatedOn().getTime(), is(1469557404358L));
    }
}