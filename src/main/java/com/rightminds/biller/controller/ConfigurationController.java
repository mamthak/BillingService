package com.rightminds.biller.controller;

import com.rightminds.biller.entity.Configuration;
import com.rightminds.biller.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/config")
@CrossOrigin
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping(value = "save", method = POST, consumes = "application/json")
    public void save(@RequestBody Map request) {
        Configuration configuration = Configuration.fromMap(request);
        configurationService.save(configuration);
    }

    @RequestMapping(value = "", method = GET, params = "id", produces = "application/json")
    public Configuration get(@PathParam(value = "id") Integer id) {
        return configurationService.getById(id);
    }

    @RequestMapping(value = "", method = GET, params = "key", produces = "application/json")
    public Configuration getByName(@PathParam(value = "key") String name) {
        return configurationService.getByKey(name);
    }

    @RequestMapping(value = "", method = GET, produces = "application/json")
    public List<Configuration> getAll() {
        return configurationService.getAll();
    }
}
