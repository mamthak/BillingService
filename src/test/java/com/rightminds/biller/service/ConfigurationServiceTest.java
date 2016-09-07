package com.rightminds.biller.service;

import com.rightminds.biller.entity.Configuration;
import com.rightminds.biller.repository.ConfigurationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConfigurationServiceTest {

    @InjectMocks
    private ConfigurationService configurationService;

    @Mock
    private ConfigurationRepository repository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheConfiguration() throws Exception {
        Configuration config = new Configuration("serviceCharge", "", "10", "", "");

        configurationService.save(config);

        verify(repository).save(config);
    }

    @Test
    public void getShouldReturnConfigurationBasedOnTheIdValue() throws Exception {
        Configuration config = new Configuration("serviceCharge", "", "10", "", "");
        when(repository.findOne(any())).thenReturn(config);

        configurationService.getById(1);

        verify(repository).findById(1);
    }

    @Test
    public void getByNameShouldReturnConfigurationBasedOnTheName() throws Exception {
        Configuration config = new Configuration("serviceCharge", "", "10", "", "");
        when(repository.findOne(any())).thenReturn(config);

        configurationService.getByKey("serviceCharge");

        verify(repository).findByKey("serviceCharge");
    }

    @Test
    public void getAllShouldReturnAllTheConfigurationAsMap() throws Exception {
        Configuration serviceCharge = new Configuration("serviceCharge", "", "10", "", "");
        Configuration serviceTax = new Configuration("serviceTax", "", "10", "", "");
        when(repository.findAll()).thenReturn(Arrays.asList(serviceCharge, serviceTax));

        List<Configuration> all = configurationService.getAll();

        verify(repository).findAll();
        assertThat(all.size(), is(2));

    }
}