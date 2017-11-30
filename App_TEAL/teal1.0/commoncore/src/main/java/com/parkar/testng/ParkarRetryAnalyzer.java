package com.parkar.testng;

import java.util.Map;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class ParkarRetryAnalyzer implements IRetryAnalyzer {
	private int retryCount = 0;
	private int maxRetryCount;

	/**
	 * Below method returns 'true' if the test method has to be retried else
	 * 'false' and it takes the 'Result' as parameter of the test method that
	 * just ran
	 **/
	@Override
	public boolean retry(ITestResult result) {
		Map<String,String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
		maxRetryCount = Integer.parseInt(params.containsKey("maxRetryCount") ? params.get("maxRetryCount") : "0");
		if (retryCount < maxRetryCount) {
			result.getTestContext().getFailedTests().removeResult(result.getMethod());
			retryCount++;
			return true;
		}
		return false;
	}
}