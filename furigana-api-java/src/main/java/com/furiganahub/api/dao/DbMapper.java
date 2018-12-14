package com.furiganahub.api.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DbMapper {
	public String getDual() throws Exception;
	
	@Select("select * from t_kjv limit 100")
	public List<HashMap<String, Object>> getBibleAll();
}
