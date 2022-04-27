package com.webkorps.csvparser.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.webkorps.csvparser.entity.DataStoreResult;
import com.webkorps.csvparser.entity.Employee;

public interface EmployeeService {

	void addEmployees(List<Employee> employees);
	
	DataStoreResult storeInDatabase(MultipartFile multipartFile);
}
