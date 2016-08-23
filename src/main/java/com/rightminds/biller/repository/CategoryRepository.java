package com.rightminds.biller.repository;

import com.rightminds.biller.entity.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends CrudRepository<Category, String> {

    @Query("SELECT c FROM Category c WHERE c.deleted IS FALSE AND c.id = :id")
    Category findActiveCategoryById(@Param("id") Integer id);

    @Query("SELECT c FROM Category c WHERE c.deleted IS FALSE")
    List<Category> findAllActiveCategories();

    Category findById(Integer id);

    Category findByName(String name);

    @Modifying
    @Query("UPDATE Category c SET deleted=TRUE WHERE c.id = :id")
    void softDelete(@Param("id") Integer id);
}
