package com.rightminds.biller.repository;

import com.rightminds.biller.BillingServiceApplication;
import com.rightminds.biller.entity.Bill;
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
import java.math.BigDecimal;

import static com.rightminds.biller.model.BillStatus.IN_PROGRESS;
import static org.hamcrest.core.Is.is;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
@SpringApplicationConfiguration(classes = BillingServiceApplication.class)
@WebAppConfiguration
public class BillRepositoryTest {

    @Autowired
    private BillRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findByIdShouldReturnTheOrder() throws Exception {
        Customer customer = new Customer("Thiru", "963247955", "Perundurai");
        customerRepository.save(customer);
        Bill bill = new Bill(customer, "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(20), new BigDecimal(3), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS);
        Bill savedItem = repository.save(bill);

        Bill fromRepository = repository.findById(savedItem.getId());

        Assert.assertThat(savedItem, is(fromRepository));
    }
}