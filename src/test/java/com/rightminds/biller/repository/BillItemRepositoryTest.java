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
import java.util.Date;

import static com.rightminds.biller.model.BillStatus.IN_PROGRESS;
import static org.hamcrest.core.Is.is;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
@SpringApplicationConfiguration(classes = BillingServiceApplication.class)
@WebAppConfiguration
public class BillItemRepositoryTest {

    @Autowired
    private BillItemRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findByIdShouldReturnTheOrderItem() throws Exception {
        Customer customer = new Customer("Thiru", "963247955", "Perundurai");
        customerRepository.save(customer);
        Bill bill = new Bill(customer, "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(20), new BigDecimal(20), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS, null, null);
        Bill savedBill = billRepository.save(bill);
        Category category = categoryRepository.save(new Category("Coke", "Cool drink", "/category.jpg", new Date()));
        Item item = new Item("Coke", "Cool drink", "/item.jpg", BigDecimal.ONE, category);
        Item save = itemRepository.save(item);

        BillItem billItem = new BillItem(new Bill(savedBill.getId()), new Item(save.getId()), 1, new BigDecimal(1), new BigDecimal(10), null);
        BillItem savedItem = repository.save(billItem);

        BillItem fromRepository = repository.findById(savedItem.getId());
        Assert.assertThat(fromRepository, is(savedItem));
    }
}