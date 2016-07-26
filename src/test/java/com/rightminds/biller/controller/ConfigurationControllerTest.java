package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Configuration;
import com.rightminds.biller.service.ConfigurationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConfigurationControllerTest {

    @InjectMocks
    private ConfigurationController controller;

    @Mock
    private ConfigurationService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheConfig() throws Exception {
        HashMap map = new HashMap<>();
        map.put("key", "serviceCharge");
        map.put("value", "10");

        controller.save(map);

        ArgumentCaptor<Configuration> argumentCaptor = ArgumentCaptor.forClass(Configuration.class);
        verify(service).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getKey(), is("serviceCharge"));
        assertThat(argumentCaptor.getValue().getValue(), is("10"));
    }

    @Test
    public void getShouldReturnConfigBasedOnTheIdValue() throws Exception {
        Configuration config = new Configuration("serviceCharge", "10");
        when(service.getById(any())).thenReturn(config);

        controller.get(1);

        verify(service).getById(1);
    }

    @Test
    public void getAllShouldReturnAllInventoryItems() throws Exception {
        Configuration config = new Configuration("serviceCharge", "10");
        when(service.getById(any())).thenReturn(config);

        controller.getAll();

        verify(service).getAll();
    }
}