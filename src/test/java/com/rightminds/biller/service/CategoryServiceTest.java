package com.rightminds.biller.service;

import com.rightminds.biller.entity.Category;
import com.rightminds.biller.repository.CategoryRepository;
import com.rightminds.biller.repository.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository repository;

    @Mock
    private ItemRepository itemRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheInventoryItem() throws Exception {
        Category category = new Category("Coke", "Cool drink", "/category.jpg");

        categoryService.save(category);

        verify(repository).save(category);
    }

    @Test
    public void getShouldReturnInventoryBasedOnTheIdValue() throws Exception {
        Category category = new Category("Coke", "Cool drink", "/category.jpg");
        when(repository.findOne(any())).thenReturn(category);

        categoryService.getById(1);

        verify(repository).findActiveCategoryById(1);
    }

    @Test
    public void getAllShouldReturnAllActiveCategories() throws Exception {
        Category category = new Category("Coke", "Cool drink", "/category.jpg");
        when(repository.findOne(any())).thenReturn(category);

        categoryService.getAllActiveCategories();

        verify(repository).findAllActiveCategories();
    }

    @Test
    public void deleteShouldDeleteTheCategory() throws Exception {
        Category category = new Category(1);

        categoryService.delete(category);

        verify(repository).softDelete(1);
        verify(itemRepository).softDeleteByCategoryId(1);
    }

}