package com.furiganahub.api.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.furiganahub.api.service.DbService;

@RestController
public class DbController {
	@Autowired
	DbService dbService;
     
    @RequestMapping("/")
    public @ResponseBody String root_test() throws Exception{  
        return "Hello World";
    }
 
    @RequestMapping("/now")
    public @ResponseBody String now() throws Exception{
        return dbService.getDual();
    }
    @RequestMapping("/bible")
    public @ResponseBody List<HashMap<String, Object>> bible() throws Exception{
        return dbService.getBibleAll();
    }
}
