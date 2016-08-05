package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Category;
import com.rightminds.biller.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "save", method = POST, consumes = "application/json")
    public void save(@RequestBody Map request) {
        Category category = Category.fromMap(request);
        categoryService.save(category);
    }

    @RequestMapping(value = "", method = GET, params = "id", produces = "application/json")
    public Category get(@PathParam(value = "id") Integer id) {
        return categoryService.getById(id);
    }

    @RequestMapping(value = "", method = GET, params = "name", produces = "application/json")
    public Category getByName(@PathParam(value = "name") String name) {
        return categoryService.getByName(name);
    }

    @RequestMapping(value = "", method = GET, produces = "application/json")
    public List<Category> getAll() {
        return categoryService.getAll();
    }
}
