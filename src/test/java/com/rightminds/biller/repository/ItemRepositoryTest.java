package com.rightminds.biller.repository;

import com.rightminds.biller.BillingServiceApplication;
import com.rightminds.biller.entity.Category;
import com.rightminds.biller.entity.Item;
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
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
@SpringApplicationConfiguration(classes = BillingServiceApplication.class)
@WebAppConfiguration
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void deleteShouldDeleteTheItemBasedOnId() throws Exception {
        Category category = categoryRepository.save(new Category("Coke", "Cool drink", "/category.jpg", new Date()));
        Item item = new Item("Coke", "Cool drink", "/item.jpg", BigDecimal.ONE, category);
        Item savedItem = itemRepository.save(item);

        itemRepository.delete(new Item(savedItem.getId(), null, null, null, null, null));

        Item itemFromRepo = itemRepository.findById(savedItem.getId());
        assertNull(itemFromRepo);

    }

    @Test
    public void findByCategoryIdShouldReturnItemsBasedOnCategory() throws Exception {
        Category firstCategory = categoryRepository.save(new Category("Cool drink", "Cool drink", "/category.jpg", new Date()));
        Category secondCategory = categoryRepository.save(new Category("Hot Drink", "Hot drink", "/category.jpg", new Date()));
        Item firstItem = new Item("Coke", "Cool drink", "/item.jpg", BigDecimal.ONE, firstCategory);
        Item secondItem = new Item("Coffee", "Hot drink", "/item.jpg", BigDecimal.ONE, secondCategory);
        Item savedItem = itemRepository.save(firstItem);
        itemRepository.save(secondItem);

        List<Item> itemsBelongingToCategory = itemRepository.findAllByCategoryId(firstCategory.getId());

        assertThat(itemsBelongingToCategory.size(), is(1));
        assertThat(itemsBelongingToCategory.get(0), is(savedItem));
    }

    @Test
    public void deleteByCategoryIdShouldDeleteAllItemsBelongingToTheCategory() throws Exception {
        Category category = categoryRepository.save(new Category("Coke", "Cool drink", "/category.jpg", new Date()));
        Item firstItem = new Item("Coke", "Cool drink", "/item.jpg", BigDecimal.ONE, category);
        Item secondItem = new Item("Coke", "Cool drink", "/item.jpg", BigDecimal.ONE, category);
        itemRepository.save(firstItem);
        itemRepository.save(secondItem);
        entityManager.flush();
        entityManager.clear();

        itemRepository.softDeleteByCategoryId(category.getId());

        List<Item> fromRepository = itemRepository.findAllByCategoryId(category.getId());
        assertThat(fromRepository.get(0).isDeleted(), is(true));
        assertThat(fromRepository.get(1).isDeleted(), is(true));
    }

    @Test
    public void findAllActiveItemsByCategoryShouldReturnAllActiveItemsBelongingToTheCategory() throws Exception {
        Category firstCategory = categoryRepository.save(new Category("Cool drink", "Cool drink", "/category.jpg", new Date()));
        Item firstItem = new Item("Coke", "Cool drink", "/item.jpg", BigDecimal.ONE, firstCategory);
        Item secondItem = new Item("Coffee", "Hot drink", "/item.jpg", BigDecimal.ONE, firstCategory);
        secondItem.setDeleted(true);
        Item savedItem = itemRepository.save(firstItem);
        itemRepository.save(secondItem);

        List<Item> itemsBelongingToCategory = itemRepository.findAllActiveItemsByCategoryId(firstCategory.getId());

        assertThat(itemsBelongingToCategory.size(), is(1));
        assertThat(itemsBelongingToCategory.get(0), is(savedItem));
    }
}