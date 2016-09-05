package com.rightminds.biller.repository;

import com.rightminds.biller.BillingServiceApplication;
import com.rightminds.biller.entity.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
@SpringApplicationConfiguration(classes = BillingServiceApplication.class)
@WebAppConfiguration
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void findByIdShouldReturnTheCategory() throws Exception {
        Category category = new Category("Coke", "Cool drink", "/category.jpg", new Date());
        Category savedItem = repository.save(category);

        Category fromRepository = repository.findActiveCategoryById(savedItem.getId());

        Assert.assertThat(savedItem, is(fromRepository));
    }

    @Test
    public void findByIdShouldNotReturnDeletedCategory() throws Exception {
        Category category = new Category("Coke", "Cool drink", "/category.jpg", new Date());
        category.setDeleted(true);
        Category savedItem = repository.save(category);

        Category fromRepository = repository.findActiveCategoryById(savedItem.getId());

        assertNull(fromRepository);
    }

    @Test
    public void deleteShouldDeleteTheCategory() throws Exception {
        Category category = new Category("Coke", "Cool drink", "/category.jpg", new Date());
        Category savedCategory = repository.save(category);
        entityManager.flush();
        entityManager.clear();

        repository.softDelete(savedCategory.getId());

        Category deletedCategory = repository.findActiveCategoryById(savedCategory.getId());
        assertNull(deletedCategory);
    }

    @Test
    public void findAllByCategoryIdShouldReturnAllNonDeletedCategories() throws Exception {
        Category firstCategory = repository.save(new Category("Coke", "Cool drink", "/category.jpg", new Date()));
        Category deletedCategory = new Category("Coke", "Cool drink", "/category.jpg", new Date());
        repository.save(deletedCategory);
        repository.softDelete(deletedCategory.getId());
        entityManager.flush();
        entityManager.clear();

        List<Category> activeCategories = repository.findAllActiveCategories();
        
        assertThat(activeCategories.contains(deletedCategory), is(false));
    }
}