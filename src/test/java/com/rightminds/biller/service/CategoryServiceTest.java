package com.rightminds.biller.service;

import com.rightminds.biller.entity.Category;
import com.rightminds.biller.repository.CategoryRepository;
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

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheInventoryItem() throws Exception {
        Category category = new Category("Coke", "Cool drink");

        categoryService.save(category);

        verify(repository).save(category);
    }

    @Test
    public void getShouldReturnInventoryBasedOnTheIdValue() throws Exception {
        Category category = new Category("Coke", "Cool drink");
        when(repository.findOne(any())).thenReturn(category);

        categoryService.getById(1);

        verify(repository).findById(1);
    }

}