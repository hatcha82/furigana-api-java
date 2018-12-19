package com.furiganahub.api.common.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.furiganahub.api.common.service.DBService;

@RestController

public class DevReqeustController {
	@Autowired
	DBService dbService;

	@GetMapping("/getPathParamEcho/{value}")
	public Object getPathParamEcho(@PathVariable Object value) throws Exception {
		return value;
	}

	@GetMapping("/getURLParamEcho")
	public Object getURLParamEcho(@RequestParam Map<String, Object> param) throws Exception {
		return param;
	}

	@PostMapping("/postJsonParamEcho")
	public Object postJsonParamEcho(@RequestBody Object param) throws Exception {
		return param;
	}
}
