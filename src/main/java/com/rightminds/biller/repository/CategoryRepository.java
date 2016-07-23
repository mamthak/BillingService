package com.rightminds.biller.repository;

import com.rightminds.biller.entity.Category;
import com.rightminds.biller.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {

    Category findById(Integer id);

    Category findByName(String name);
}
