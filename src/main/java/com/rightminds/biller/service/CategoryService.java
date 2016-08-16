package com.rightminds.biller.service;

import com.rightminds.biller.entity.Category;
import com.rightminds.biller.repository.CategoryRepository;
import com.rightminds.biller.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    // TODO: Move the repository to item service
    @Autowired
    private ItemRepository itemRepository;

    public void save(Category category) {
        repository.save(category);
    }

    public Category getById(Integer id) {
        return repository.findActiveCategoryById(id);
    }

    public Category getByName(String name) {
        return repository.findByName(name);
    }

    public List<Category> getAllActiveCategories() {
        return repository.findAllActiveCategories();
    }

    public List<Category> getAll() {
        return (List<Category>) repository.findAll();
    }

    @Transactional
    public void delete(Category category) {
        repository.softDelete(category.getId());
        itemRepository.softDeleteByCategoryId(category.getId());
    }
}
