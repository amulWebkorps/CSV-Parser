package com.webkorps.csvparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.webkorps.csvparser.entity.DataStoreResult;
import com.webkorps.csvparser.service.EmployeeService;

@SpringBootTest
class CsvParserApplicationTests {
	
	@Autowired
	private EmployeeService employeeService;
	
	private final String filePath = "src/main/resources/static/files/empdetails.csv";

	
	@Test
	void testStoreInDatabase() {
		DataStoreResult dsr = new DataStoreResult();
		dsr.setProcessed(15);
		dsr.setSkipped(38);
		File file = new File(filePath);
		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MultipartFile multipartFile = null;
		try {
			multipartFile = new MockMultipartFile("file",
			            file.getName(), "text/csv",input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(dsr.getProcessed(), employeeService.storeInDatabase(multipartFile).getProcessed(),"Successfull" );
		assertEquals(dsr.getSkipped(), employeeService.storeInDatabase(multipartFile).getSkipped(),"Successfull" );
	}

}
