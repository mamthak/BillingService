package com.rightminds.biller.repository;

import com.rightminds.biller.entity.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends CrudRepository<Menu, String> {

    Menu findById(Integer id);
}
