
package com.webkorps.csvparser.service.impl;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webkorps.csvparser.entity.DataStoreResult;
import com.webkorps.csvparser.entity.Employee;
import com.webkorps.csvparser.repository.EmployeeRepository;
import com.webkorps.csvparser.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void addEmployees(List<Employee> employees) {
		employeeRepository.saveAll(employees);
	}
	
	@Override
	public DataStoreResult storeInDatabase(MultipartFile multipartFile) {
		DataStoreResult dsr = new DataStoreResult();
		int processed = 0;
		int skipped = 0;
		try {

			Reader in = new InputStreamReader(multipartFile.getInputStream());
			Iterable<CSVRecord> records= CSVFormat.EXCEL.builder().setSkipHeaderRecord(true).setHeader("Employee Id", "Employee Name", "Age","Country").build().parse(in).getRecords();
			List<Employee> emps = new ArrayList<Employee>();
			for (CSVRecord record : records) {
			    try {
			    Employee emp = new Employee();
			    emp.setEmployeeId(Long.parseLong(record.get("Employee Id")));
			    if( record.get("Employee Name").isEmpty() ||  record.get("Employee Name")==null) {
			    	skipped++;
			    	continue;
			    }
			    emp.setEmployeeName( record.get("Employee Name"));
			    emp.setAge(Integer.parseInt(record.get("Age")));
			    if( record.get("Country").isEmpty() ||  record.get("Country")==null) {
			    	skipped++;
			    	continue;
			    }
			    emp.setCountry(record.get("Country"));
			    emps.add(emp);
			    processed++;
			    }catch (Exception e) {
			    	skipped++;
					logger.error(e.getMessage());
					continue;
				}
			}
			addEmployees(emps);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		dsr.setProcessed(processed);
		dsr.setSkipped(skipped);
		
		return dsr;
	}

}
