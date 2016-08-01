package com.rightminds.biller.repository;

import com.rightminds.biller.entity.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends CrudRepository<Configuration, String> {

    Configuration findById(Integer id);

    Configuration findByKey(String key);
}
