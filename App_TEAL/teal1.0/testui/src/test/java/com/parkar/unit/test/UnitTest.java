package com.parkar.unit.test;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.parkar.testng.BaseUITest;
import com.parkar.testng.TestParameters;

public class UnitTest extends BaseUITest{
	
	@DataProvider(name = "BasicDataProvider")
	public Object[][] getTestData() {
		Object[][] data = new Object[][] {
				{ new TestParameters("TestCase1", "Sample test 1"), new Integer(19), new String("Abc1"), Arrays.asList("One", "Two", "Three")},
				{ new TestParameters("TestCase2", "Sample test 2"), new Integer(20), new String("Abc2"), Arrays.asList("Four") }};
		return data;
	}

	@Test(groups = "DataProviderTest",description = " Data driven testing", dataProvider = "BasicDataProvider")
	public void testSample1(TestParameters testParams, Integer i, String string, List<String> lsit) {
		reporter.reportStep("Step 1: Test data provider for test case name and description");
	}

	@Test(groups = "AssertionTests", description = " Assertion testing")
	public void testPositveAssertionArea() {
		reporter.reportStep("Step 1: Test assertion");
		assertion.verifyTrue(true, "Verify True functionality");
		assertion.verifyFalse(false, "Verify False functionality");
		assertion.verifyEquals("Data1", "Data1", "Verify Equals functionality");
		assertion.verifyNotEquals("Data1", "Data2","Verify Not Equals functionality");
		reporter.reportStep("Step 2: Tested assertion");
	}

	@Test(groups = "AssertionTests", description = " Assertion negative testing")
	public void testNegativeAssertionArea() {
		reporter.reportStep("Step 1: Test negative assertion");
		assertion.verifyTrue(false, "Verify True functionality");
		assertion.verifyFalse(true, "Verify False functionality");
		assertion.verifyEquals("Data1", "Data2", "Verify Equals functionality");
		assertion.verifyNotEquals("Data1", "Data1","Verify Not Equals functionality");
		reporter.reportStep("Step 2: Tested negative assertion");
	}

	@Test(groups = "ExceptionTest", description = " Exception testing")
	public void testExceptionTesting() throws Exception {
		reporter.reportStep("Step 1: Test Exception");
		reporter.reportStep("Step 2: Tested Exception");
		throw new Exception("Operation failed");
	}
}
