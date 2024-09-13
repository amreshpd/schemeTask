package com.nt.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nt.entity.Scheme;
import com.nt.entity.SchemeRespone;
import com.nt.repository.SchemeRepository;

@Service
public class SchemeServiceImpl implements ISchemeServiceMgmt {

	@Autowired
	private SchemeRepository repository;
	
	@Override
	public void findAllService() {
		RestTemplate restTemplate = new RestTemplate();
		//call through link
	     String url="https://api.mfapi.in/mf";
	     ResponseEntity<List<Scheme>> response = restTemplate.exchange(
	         url,
	         HttpMethod.GET,
	         null,
	         new ParameterizedTypeReference<List<Scheme>>() {}
	     );
	     //save the all data
		List<Scheme> schemes = response.getBody();
        if (schemes != null) {
           repository.saveAll(schemes);
        }
	}

	@Override
	public List<Scheme> showSchemeByName(Map<String, String> schemeName) {
		String name = schemeName.get("scheme_name");
		List<Scheme> scheme = repository.findBySchemeNameContainingIgnoreCase(name);
		System.out.println(schemeName);
		return scheme;
	}

	@Override
	public Map<String, Object> showSchmeById(Map<String, Object> request) {
		Integer schemeId = (Integer) request.get("scheme_code");
	    String filter = (String) request.get("filter");
	    String url = "https://api.mfapi.in/mf/" + schemeId;

	    // Fetch data
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<SchemeRespone> response = restTemplate.getForEntity(url, SchemeRespone.class);
	    SchemeRespone schemeResponse = response.getBody();

	    // Filter data logic (Example for 1M filter)
	    Map<String, Object> filteredData = filterSchemeData(schemeResponse, filter);

	    // Prepare Response
	    Map<String, Object> reponsedata = new HashMap<>();
	    reponsedata.put("response", filteredData);
		return reponsedata;
	}
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private Map<String, Object> filterSchemeData(SchemeRespone scheme, String filter) {	

	        // Get the current date
	        LocalDate currentDate = LocalDate.now();

	        // Calculate the start date based on the filter
	        LocalDate startDate;
	        switch (filter) {
	            case "1M":
	                startDate = currentDate.minus(1, ChronoUnit.MONTHS);
	                break;
	            case "1W":
	                startDate = currentDate.minus(1, ChronoUnit.WEEKS);
	                break;
	            case "1Y":
	                startDate = currentDate.minus(1, ChronoUnit.YEARS);
	                break;
	            case "5Y":
	                startDate = currentDate.minus(5, ChronoUnit.YEARS);
	                break;
	            default:
	                throw new IllegalArgumentException("Invalid filter: " + filter);
	        }

	        // Prepare lists to hold filtered dates and values
	        List<String> filteredDates = new ArrayList<>();
	        List<String> filteredValues = new ArrayList<>();

	        // Assuming schemeResponse.getData() returns a list of date-value pairs
	        List<List<String>> data = scheme.getData();
	        if (data != null && data.size() == 2) {
	            List<String> dates = data.get(0);
	            List<String> values = data.get(1);
	            for (int i = 0; i < dates.size(); i++) {
	                LocalDate date = LocalDate.parse(dates.get(i), DATE_FORMATTER);

	                // Check if the date falls within the filter range
	                if (!date.isBefore(startDate)) {
	                    filteredDates.add(dates.get(i));
	                    filteredValues.add(values.get(i));
	                }
	            }
	        }

	        // Prepare the response map
	        Map<String, Object> response = new HashMap<>();
	        response.put("schemeCode", scheme.getSchemeCode());
	        response.put("schemeName", scheme.getSchemeName());
	        response.put("data", List.of(filteredDates, filteredValues));
	        return response;

	}

	
}
