package com.furiganahub.api.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface SongDao {
	@Select("select * from t_kjv limit 100")
	public List<HashMap<String, Object>> getAll();

	@Select("select * from t_kjv limit #{offset}")
	public List<HashMap<String, Object>> getAllByLimit(HashMap<String, Object> param);

	public List<HashMap<String, Object>> getBible(HashMap<String, Object> param) throws Exception;

}
