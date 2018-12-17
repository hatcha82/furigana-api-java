package com.furiganahub.api.service;

import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furiganahub.api.dao.DbDao;

@Service
public class DbService {
	@Autowired
	DbDao dbMapper;

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

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

	public void excelExport(HttpServletResponse response, String mapperId, HashMap<String, Object> param) {
		SqlSession sqlSession = sqlSessionFactory.openSession();

		// 메모리에 100개의 행을 유지합니다. 행의 수가 넘으면 디스크에 적습니다.
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		Sheet sheet = wb.createSheet();

		try {
			sqlSession.select(mapperId, param, new ResultHandler<HashMap<String, Object>>() {
				@Override
				public void handleResult(ResultContext context) {
					HashMap<String, Object> dbRow = (HashMap<String, Object>) context.getResultObject();
					Row row = sheet.createRow(context.getResultCount() - 1);
					Cell cell = null;
					cell = row.createCell(0);
					cell.setCellValue(String.valueOf(dbRow.get("id")));
					cell = row.createCell(1);
					cell.setCellValue(String.valueOf(dbRow.get("artist")));
					cell = row.createCell(2);
					cell.setCellValue(String.valueOf(dbRow.get("title")));
					cell = row.createCell(3);
					cell.setCellValue(String.valueOf(dbRow.get("lyrics")));
				}
			});

			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"test.xlsx\""));
			wb.write(response.getOutputStream());

		} catch (Exception e) {
			response.setHeader("Set-Cookie", "fileDownload=false; path=/");
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Content-Type", "text/html; charset=utf-8");

			OutputStream out = null;
			try {
				out = response.getOutputStream();
				byte[] data = new String("fail..").getBytes();
				out.write(data, 0, data.length);
			} catch (Exception ignore) {
				ignore.printStackTrace();
			} finally {
				if (out != null)
					try {
						out.close();
					} catch (Exception ignore) {
					}
			}

		} finally {
			sqlSession.close();

			// 디스크 적었던 임시파일을 제거합니다.
			wb.dispose();

			try {
				wb.close();
			} catch (Exception ignore) {
			}
		}
	}

	public Object excuteSP(String string, HashMap<String, Object> param) {
		String PROCEDURE_STR = "PCM_GRID_MASTER_L004('SELECT 1;')";
		param.put("PROCEDURE_STR", PROCEDURE_STR);

		param.put("RESULT_SET", dbMapper.selectList("com.furiganahub.api.dao.DbDao.procedureQuery", param));
		return param;
	}

}
