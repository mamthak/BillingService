package com.rightminds.biller.entity;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ConfigurationTest {

    @Test
    public void fromMapShouldCreateConfigurationFromMap() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", "serviceCharge");
        map.put("value", "10");
        map.put("name", "Service Charge");
        map.put("defaultValue", "0");
        map.put("description", "Service charge paid to the government");

        Configuration configuration = Configuration.fromMap(map);

        assertNull(configuration.getId());
        assertThat(configuration.getKey(), is("serviceCharge"));
        assertThat(configuration.getName(), is("Service Charge"));
        assertThat(configuration.getDescription(), is("Service charge paid to the government"));
        assertThat(configuration.getDefaultValue(), is("0"));
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

    @Test
    public void toMapShouldReturnTheMap() throws Exception {
        Configuration config = new Configuration("serviceCharge", "Service Charge", "10", "0", "Service charge paid");

        Map map = config.toMap();

        assertThat(map.get("name"), is("Service Charge"));
        assertThat(map.get("key"), is("serviceCharge"));
        assertThat(map.get("description"), is("Service charge paid"));
        assertThat(map.get("value"), is("10"));
        assertThat(map.get("defaultValue"), is("0"));
    }
}