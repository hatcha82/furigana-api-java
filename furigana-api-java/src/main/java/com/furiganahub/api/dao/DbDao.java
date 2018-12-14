package com.furiganahub.api.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DbDao {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * Mybatis Select 단일 Queury를 실행합니다.
	 * 
	 * @param mapperId Mybatis Query ID
	 * @param param    mapper namespace + id 예) com.furiganahub.api.dao.test.testID
	 * @return Object List<HashMap<String, Object>>형태
	 */

	public Object selectOne(String mapperId, HashMap<String, Object> param) {
		return this.sqlSession.selectOne(mapperId, param);
	}

	/**
	 * Mybatis Select Queury를 실행합니다.
	 * 
	 * @param mapperId Mybatis Query ID
	 * @param param    mapper namespace + id 예) com.furiganahub.api.dao.test.testID
	 * @return Object List<HashMap<String, Object>>형태
	 */
	public Object selectList(String mapperId, HashMap<String, Object> param) {
		return this.sqlSession.selectList(mapperId, param);
	}

	/**
	 * Mybatis insert Queury를 실행합니다.
	 * 
	 * @param mapperId Mybatis Query ID
	 * @param param    mapper namespace + id 예) com.furiganahub.api.dao.test.testID
	 * @return insert Count
	 */
	public int insert(String mapperId, HashMap<String, Object> param) {
		return this.sqlSession.insert(mapperId, param);
	}

	/**
	 * Mybatis update Queury를 실행합니다.
	 * 
	 * @param mapperId Mybatis Query ID
	 * @param param    mapper namespace + id 예) com.furiganahub.api.dao.test.testID
	 * @return update Count
	 */
	public int update(String mapperId, HashMap<String, Object> param) {
		return this.sqlSession.update(mapperId, param);
	}

	/**
	 * Mybatis delete Queury를 실행합니다.
	 * 
	 * @param mapperId Mybatis Query ID
	 * @param param    mapper namespace + id 예) com.furiganahub.api.dao.test.testID
	 * @return delete Count
	 */
	public int delete(String mapperId, HashMap<String, Object> param) {
		return this.sqlSession.delete(mapperId, param);
	}

}
