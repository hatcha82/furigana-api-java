package com.furiganahub.api.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furiganahub.api.dao.DbMapper;
@Service
public class DbService {
	@Autowired
    DbMapper dbMapper;
    public String getDual() throws Exception{
        return dbMapper.getDual();
    }
	public List<HashMap<String, Object>> getBibleAll() {
		 return dbMapper.getBibleAll();
	}

}
