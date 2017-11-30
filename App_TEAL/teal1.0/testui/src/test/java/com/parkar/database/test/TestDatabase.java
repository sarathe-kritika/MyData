package com.parkar.database.test;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.utils.database.DBUtils;

public class TestDatabase {
	
	@Test(groups = "TEAL", testName = "test_ALM82069_VerifyDbData", description = "Verify Database functionality")
	public void test_ALM82069_VerifyDbData() throws ParkarCoreCommonException {
		
		// Read test data
		List<Map<String, String>> testData = DBUtils.readDB("mysql", "select * from credentials");
		System.out.println(testData);

	}

}
