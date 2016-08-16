package com.rightminds.biller.repository;

import com.rightminds.biller.entity.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, String> {

    Item findById(Integer id);

    List<Item> findAllByCategoryId(Integer id);

    @Query("SELECT i FROM Item i WHERE i.deleted IS FALSE AND i.category.id = :category_id")
    List<Item> findAllActiveItemsByCategoryId(@Param("category_id") Integer categoryId);

    @Modifying
    @Query("UPDATE Item i SET deleted=TRUE WHERE i.category.id = :category_id")
    void softDeleteByCategoryId(@Param("category_id") Integer id);

}
