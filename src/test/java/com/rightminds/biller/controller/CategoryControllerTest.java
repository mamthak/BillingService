package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Category;
import com.rightminds.biller.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void saveShouldSaveTheInventoryItem() throws Exception {
        HashMap map = new HashMap<>();
        map.put("name", "Coke");
        map.put("description", "Cool Drink");

        categoryController.save(map);

        ArgumentCaptor<Category> argumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryService).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName(), is("Coke"));
        assertThat(argumentCaptor.getValue().getDescription(), is("Cool Drink"));
    }

    @Test
    public void getShouldReturnInventoryBasedOnTheIdValue() throws Exception {
        Category category = new Category("Coke", "Cool drink", "/category.jpg");
        when(categoryService.getById(any())).thenReturn(category);

        categoryController.get(1);

        verify(categoryService).getById(1);
    }

    @Test
    public void getAllShouldReturnAllInventoryItems() throws Exception {
        Category category = new Category("Coke", "Cool drink", "/category.jpg");
        when(categoryService.getById(any())).thenReturn(category);

        categoryController.getAll();

        verify(categoryService).getAll();
    }
}