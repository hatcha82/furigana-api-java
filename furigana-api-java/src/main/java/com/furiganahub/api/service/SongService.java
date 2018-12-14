package com.furiganahub.api.service;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furiganahub.api.dao.SongDao;
@Service
public class SongService {
	@Autowired
	SongDao songDao;
	
	public Object getAll(){
		return songDao.getAll();
	}
	public Object getAllByLimit(HashMap<String, Object> param) {
		return songDao.getAllByLimit(param);
	}
	public Object getBible(HashMap<String, Object> param) throws Exception {
		return songDao.getBible(param);
	}	
	
}
