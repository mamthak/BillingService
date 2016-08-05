package com.rightminds.biller.service;

import com.rightminds.biller.entity.Configuration;
import com.rightminds.biller.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository repository;

    public void save(Configuration customer) {
        repository.save(customer);
    }

    public Configuration getById(Integer id) {
        return repository.findById(id);
    }

    public List<Configuration> getAll() {
        return (List<Configuration>) repository.findAll();
    }

    public Configuration getByKey(String key) {
        return repository.findByKey(key);
    }
}
