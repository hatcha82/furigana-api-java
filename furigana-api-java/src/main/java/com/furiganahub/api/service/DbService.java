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

	public Object excuteSP(String string, HashMap<String, Object> param) {
		String PROCEDURE_STR = "PCM_GRID_MASTER_L004('SELECT 1;')";
		param.put("PROCEDURE_STR", PROCEDURE_STR);

		param.put("RESULT_SET", dbMapper.selectList("com.furiganahub.api.dao.DbDao.procedureQuery", param));
		return param;
	}

}
