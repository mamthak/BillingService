package com.rightminds.biller.repository;

import com.rightminds.biller.BillingServiceApplication;
import com.rightminds.biller.entity.*;
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

import static org.hamcrest.core.Is.is;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
@SpringApplicationConfiguration(classes = BillingServiceApplication.class)
@WebAppConfiguration
public class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findByIdShouldReturnTheOrderItem() throws Exception {
        Customer customer = new Customer("Thiru", "963247955", "Perundurai");
        customerRepository.save(customer);
        Order order = new Order(customer, "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), false);
        orderRepository.save(order);
        Category category = categoryRepository.save(new Category("Coke", "Cool drink"));
        Item item = new Item("Coke", "Cool drink", BigDecimal.ONE, category);
        itemRepository.save(item);

        OrderItem orderItem = new OrderItem(order, item, BigDecimal.ONE, new BigDecimal(1), new BigDecimal(10));
        OrderItem savedItem = repository.save(orderItem);

        OrderItem fromRepository = repository.findById(savedItem.getId());
        Assert.assertThat(fromRepository, is(savedItem));
    }
}