package com.furiganahub.api.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furiganahub.api.dao.DbDao;

@Service
public class DbService {
	@Autowired
	DbDao dbMapper;

	public Object selectOne(String mapperId, HashMap<String, Object> param) {
		return dbMapper.selectOne(mapperId, param);
	}

	public Object selectList(String mapperId, HashMap<String, Object> param) {
		return dbMapper.selectList(mapperId, param);
	}

	public Object insert(String mapperId, HashMap<String, Object> param) {
		return dbMapper.insert(mapperId, param);
	}

	public Object update(String mapperId, HashMap<String, Object> param) {
		return dbMapper.update(mapperId, param);
	}

	public Object delete(String mapperId, HashMap<String, Object> param) {
		return dbMapper.delete(mapperId, param);
	}

}
