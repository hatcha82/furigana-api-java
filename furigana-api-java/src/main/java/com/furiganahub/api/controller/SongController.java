package com.furiganahub.api.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.furiganahub.api.service.SongService;

@RestController
@RequestMapping("/songs")
public class SongController{
	@Autowired
	SongService songService;
	@RequestMapping(method = RequestMethod.GET)
	public Object getAll(){
		return songService.getAll();
	}
	@RequestMapping(value="/{offset}", method = RequestMethod.GET)
	public  Object getAllByLimit(@PathVariable int offset){
		
		HashMap<String,Object> param = new HashMap<String,Object>();
		param.put("offset", offset);
		return  songService.getAllByLimit(param);
	}
	
	@RequestMapping(value="/bibles/{limit}", method = RequestMethod.GET)
	public  Object getBible( @PathVariable int limit){
		
		HashMap<String,Object> param = new HashMap<String,Object>();
		param.put("limit", limit);
		try {
			return  songService.getBible(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
}
