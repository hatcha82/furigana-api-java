package com.furiganahub.api.common.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.furiganahub.api.common.service.DBService;

@RestController
public class DBController {
	@Autowired
	DBService dbService;

	private HashMap<String, Object> excuteQuery(@PathVariable String queryId, HashMap<String, Object> param) {
		HashMap<String, Object> resp = new HashMap<String, Object>();
		resp.put("QUERY_ID", queryId);
		resp.put("I_PARAMS", param);
		resp.put("O_SERVER_ERROR_FLAG", "N");
		try {
			resp.put("O_RESULT", dbService.selectList(queryId, param));
		} catch (Exception e) {
			resp.put("O_SERVER_ERROR_FLAG", "Y");
			resp.put("O_SERVER_ERROR_MSG", e.getMessage());
		}
		return resp;
	}

	private Object excuteMQuery(HashMap<String, Object> param) {
		HashMap<String, Object> resp = new HashMap<String, Object>();
		List<String> QUERY_ID_LIST = (List<String>) param.get("QUERY_ID_LIST");
		int count = 0;
		try {
			for (String QUERYI_ID : QUERY_ID_LIST) {
				resp.put("O_RESULT" + String.format("%02d", count), dbService.selectList(QUERYI_ID, param));
				count++;
			}
		} catch (Exception e) {
			resp.put("O_SERVER_ERROR_FLAG", "Y");
			resp.put("O_SERVER_ERROR_MSG", e.getMessage());
		}

		return resp;
	}

	@GetMapping("/")
	public String index() {
		return "DBController";
	}

	@GetMapping("/now")
	public Object now() {
		return this.excuteQuery("common.getNow", new HashMap<String, Object>());
	}

	@GetMapping("/URLQuery/NEW_SID")
	public HashMap<String, Object> SID() {
		HashMap<String, Object> param = new HashMap<String, Object>();
		return this.excuteQuery("common.getSID", param);
	}

	@GetMapping("/URLQuery/{queryId}/{SID}")
	public HashMap<String, Object> URLQueryBySid(@PathVariable String queryId, @PathVariable Object SID) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("SID", SID);
		return this.excuteQuery(queryId, param);
	}

	@GetMapping("/URLQuery/{queryId}")
	public Object URLQueryGet(@PathVariable String queryId, @RequestParam HashMap<String, Object> param) {
		return this.excuteQuery(queryId, param);
	}

	@PostMapping("/URLQuery/{queryId}")
	public Object URLQueryPost(@PathVariable String queryId, @RequestBody HashMap<String, Object> param) {
		return this.excuteQuery(queryId, param);
	}

	@PostMapping("/URLMQuerySID")
	public Object URLMQueryPost(@RequestBody HashMap<String, Object> param) {
		return this.excuteMQuery(param);
	}

}
