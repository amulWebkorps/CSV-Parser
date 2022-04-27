package com.webkorps.csvparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webkorps.csvparser.entity.DataStoreResult;
import com.webkorps.csvparser.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	

	@PostMapping("/upload-file")
	public ResponseEntity<?> csvParse(@RequestParam("file") MultipartFile file  ){
		
		if(file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request must contain file");
			 
		}
		if(!file.getContentType().equals("text/csv")) {
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Only CSV file Parse");
		}
		DataStoreResult dsr = employeeService.storeInDatabase(file);
		return ResponseEntity.ok(dsr);
	}
	
	
	
}
