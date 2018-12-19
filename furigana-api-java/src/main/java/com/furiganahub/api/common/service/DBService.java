package com.furiganahub.api.common.service;

import java.io.OutputStream;
import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

@Service
public class DBService {
	@Autowired
	com.furiganahub.api.common.dao.DBDao dbMapper;

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

	private ArrayList<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int ColumnType = 0;
		int columns = md.getColumnCount();
		ArrayList<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		while (rs.next()) {
			Map<String, Object> row = new HashMap<String, Object>(columns);
			for (int i = 1; i <= columns; ++i) {
				ColumnType = md.getColumnType(i);
				if (Types.CLOB == ColumnType) {
					// mysql for md.getColumnLabel(i)
					// oracle for md.getColumnName(i)

					row.put(md.getColumnLabel(i), getClob(rs, i));
				} else {
					row.put(md.getColumnLabel(i), rs.getObject(i));
				}

			}
			rows.add(row);
		}
		return rows;
	}

	private Object getClob(Clob clob) {
		StringBuffer output = new StringBuffer();
		try {
			Reader input = clob.getCharacterStream();
			char[] buffer = new char[1024];
			int byteRead;
			while ((byteRead = input.read(buffer, 0, 1024)) != -1) {
				output.append(buffer, 0, byteRead);
			}
			input.close();
		} catch (Exception e) {

		}
		return output.toString();

	}

	private String getClob(ResultSet rs, int i) {
		// TODO Auto-generated method stub
		String text = "";
		Clob clob;
		try {

			clob = rs.getClob(i);
			if (clob != null) {
				text = clob.getSubString(1, (int) clob.length());
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			text = "";
		}

		// materialize CLOB onto client

		/*
		 * 
		 * StringBuffer output = new StringBuffer();
		 * 
		 * try { Reader input = rs.getCharacterStream("content"); char[] buffer = new
		 * char[1024]; int byteRead; while((byteRead=input.read(buffer,0,1024))!=-1){
		 * output.append(buffer,0,byteRead); } input.close(); } catch (Exception e) { //
		 * TODO: handle exception } output.toString();
		 */
		return text;
	}

}
