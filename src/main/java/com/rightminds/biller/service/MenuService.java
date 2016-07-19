package com.rightminds.biller.service;

import com.rightminds.biller.entity.Menu;
import com.rightminds.biller.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository repository;

    public void save(Menu menu) {
        repository.save(menu);
    }

    public List<Menu> getAll() {
        return (List<Menu>) repository.findAll();
    }
}
