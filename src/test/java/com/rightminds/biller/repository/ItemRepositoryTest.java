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

import javax.transaction.Transactional;
import java.math.BigDecimal;
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

    @Test
    public void deleteShouldDeleteTheItemBasedOnId() throws Exception {
        Category category = categoryRepository.save(new Category("Coke", "Cool drink", "/category.jpg"));
        Item item = new Item("Coke", "Cool drink", "/item.jpg", BigDecimal.ONE, category);
        Item savedItem = itemRepository.save(item);

        itemRepository.delete(new Item(savedItem.getId(), null, null, null, null, null));

        Item itemFromRepo = itemRepository.findById(savedItem.getId());
        assertNull(itemFromRepo);

    }

    @Test
    public void findByCategoryIdShouldReturnItemsBasedOnCategory() throws Exception {
        Category firstCategory = categoryRepository.save(new Category("Cool drink", "Cool drink", "/category.jpg"));
        Category secondCategory = categoryRepository.save(new Category("Hot Drink", "Hot drink", "/category.jpg"));
        Item firstItem = new Item("Coke", "Cool drink", "/item.jpg", BigDecimal.ONE, firstCategory);
        Item secondItem = new Item("Coffee", "Hot drink", "/item.jpg", BigDecimal.ONE, secondCategory);
        Item savedItem = itemRepository.save(firstItem);
        itemRepository.save(secondItem);

        List<Item> itemsBelongingToCategory = itemRepository.findAllByCategoryId(firstCategory.getId());

        assertThat(itemsBelongingToCategory.size(), is(1));
        assertThat(itemsBelongingToCategory.get(0), is(savedItem));
    }
}