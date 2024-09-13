package com.nt.ms;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.entity.Scheme;
import com.nt.service.ISchemeServiceMgmt;

@RestController
@RequestMapping("/scheme")
public class SchemeController {
	@Autowired
	private ISchemeServiceMgmt service;

	@GetMapping("/save")
	public ResponseEntity<String> saveScheme() {
		service.findAllService();
		return new ResponseEntity<String>("Data strored successfully",HttpStatus.OK);
	}
	
	@PostMapping("/find")
	public ResponseEntity<List<Scheme>> showSchemeByName(@RequestBody Map<String, String> schemeName){
		List<Scheme> name = service.showSchemeByName(schemeName);
		return new ResponseEntity<List<Scheme>>(name,HttpStatus.OK);
	}
	@PostMapping("/findById")
	public ResponseEntity<Map<String, Object>>showSchemeById(@RequestBody Map<String, Object> request){
		Map<String, Object> map = service.showSchmeById(request);
		return new ResponseEntity<Map<String, Object>>(map,HttpStatus.OK);
	}
}
