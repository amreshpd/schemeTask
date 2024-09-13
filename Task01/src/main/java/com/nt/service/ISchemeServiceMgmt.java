package com.nt.service;

import java.util.List;
import java.util.Map;

import com.nt.entity.Scheme;

public interface ISchemeServiceMgmt {
	public void findAllService();
	public List<Scheme> showSchemeByName(Map<String, String> schemeName);
	public Map<String,Object> showSchmeById(Map<String, Object> request);
}
