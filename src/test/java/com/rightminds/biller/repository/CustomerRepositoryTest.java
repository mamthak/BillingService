package com.rightminds.biller.repository;

import com.rightminds.biller.BillingServiceApplication;
import com.rightminds.biller.entity.Customer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.hamcrest.core.Is.is;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
@SpringApplicationConfiguration(classes = BillingServiceApplication.class)
@WebAppConfiguration
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    public void findByIdShouldReturnTheCustomer() throws Exception {
        Customer customer = new Customer("Thiru", "9876543210", "Perundurai");
        Customer savedItem = repository.save(customer);

        Customer fromRepository = repository.findById(savedItem.getId());

        Assert.assertThat(savedItem, is(fromRepository));
    }
}