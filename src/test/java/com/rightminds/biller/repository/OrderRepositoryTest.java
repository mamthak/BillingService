package com.rightminds.biller.repository;

import com.rightminds.biller.BillingServiceApplication;
import com.rightminds.biller.entity.Category;
import com.rightminds.biller.entity.Customer;
import com.rightminds.biller.entity.Order;
import com.rightminds.biller.model.OrderStatus;
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

import static com.rightminds.biller.model.OrderStatus.IN_PROGRESS;
import static org.hamcrest.core.Is.is;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
@SpringApplicationConfiguration(classes = BillingServiceApplication.class)
@WebAppConfiguration
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findByIdShouldReturnTheOrder() throws Exception {
        Customer customer = new Customer("Thiru", "963247955", "Perundurai");
        customerRepository.save(customer);
        Order order = new Order(customer, "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS);
        Order savedItem = repository.save(order);

        Order fromRepository = repository.findById(savedItem.getId());

        Assert.assertThat(savedItem, is(fromRepository));
    }
}