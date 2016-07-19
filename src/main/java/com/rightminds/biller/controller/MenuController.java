package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Menu;
import com.rightminds.biller.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "save", method = POST, consumes = "application/json")
    public void save(@RequestBody Map map) {
        Menu menu = Menu.fromMap(map);
        menuService.save(menu);
    }

    @RequestMapping(value = "all", method = GET, consumes = "application/json")
    public List<Menu> all() {
        return menuService.getAll();
    }
}
