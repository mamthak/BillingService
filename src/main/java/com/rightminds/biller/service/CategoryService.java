package com.rightminds.biller.service;

import com.rightminds.biller.entity.Category;
import com.rightminds.biller.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public void save(Category category) {
        repository.save(category);
    }

    public Category getById(Integer id) {
        return repository.findById(id);
    }
    public Category getByName(String name) {
        return repository.findByName(name);
    }

    public List<Category> getAll() {
        return (List<Category>) repository.findAll();
    }
}
