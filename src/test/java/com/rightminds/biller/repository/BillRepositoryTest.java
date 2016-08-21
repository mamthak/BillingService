package com.rightminds.biller.repository;

import com.rightminds.biller.BillingServiceApplication;
import com.rightminds.biller.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;

import static com.rightminds.biller.model.BillStatus.IN_PROGRESS;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


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

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BillItemRepository billItemRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void findByIdShouldReturnTheOrder() throws Exception {
        Customer customer = new Customer("Thiru", "963247955", "Perundurai");
        customerRepository.save(customer);
        Bill bill = new Bill(customer, "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(20), new BigDecimal(3), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS, null, null);
        Bill savedItem = repository.save(bill);

        Bill fromRepository = repository.findById(savedItem.getId());

        assertThat(savedItem, is(fromRepository));
    }

    @Test
    public void updateNameShouldChangeTheNameOfTheBill() throws Exception {
        Customer customer = new Customer("Thiru", "963247955", "Perundurai");
        customerRepository.save(customer);
        Bill bill = new Bill(customer, "Order 1", new BigDecimal(10), new BigDecimal(11), new BigDecimal(15), new BigDecimal(20), new BigDecimal(3), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS, null, null);
        Bill savedItem = repository.save(bill);

        repository.updateName(savedItem.getId(), "New name");

        entityManager.flush();
        entityManager.clear();
        Bill fromRepository = repository.findById(bill.getId());
        assertThat(fromRepository.getName(), is("New name"));
    }

    @Test
    public void subTotalShouldBeUpdatedOnAdditionOfBillItem() throws Exception {
        Customer customer = new Customer("Thiru", "963247955", "Perundurai");
        customerRepository.save(customer);
        Bill bill = new Bill(customer, "Order 1", new BigDecimal(10), new BigDecimal(11), null, new BigDecimal(20), new BigDecimal(3), new BigDecimal(5), new BigDecimal(5), IN_PROGRESS, null, null);
        Bill savedBill = repository.save(bill);
        Category category = new Category("Coke", "Cool drink", "/category.jpg");
        Category savedCategory = categoryRepository.save(category);
        Item item = new Item("Coke", "Cool Drink", "/item.jpg", BigDecimal.TEN, savedCategory, false, 15);
        Item savedItem = itemRepository.save(item);
        BillItem firstBillItem = new BillItem(savedBill, savedItem, 1, BigDecimal.ZERO, BigDecimal.TEN, null);
        BillItem secondBillItem = new BillItem(savedBill, savedItem, 2, BigDecimal.ZERO, new BigDecimal(2), null);
        billItemRepository.save(firstBillItem);
        billItemRepository.save(secondBillItem);
        entityManager.flush();
        entityManager.clear();

        Bill fromRepository = repository.findById(savedBill.getId());

        assertThat(fromRepository.getSubTotal(), is(new BigDecimal(12)));
    }
}