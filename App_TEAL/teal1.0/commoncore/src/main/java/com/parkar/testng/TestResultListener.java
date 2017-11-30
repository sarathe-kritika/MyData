package com.parkar.testng;

import java.util.Set;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.internal.IResultListener;

import com.parkar.assertion.ParkarSoftAssertion;
import com.parkar.report.CoreReporter;
import com.parkar.report.ParkarReporter;
import com.parkar.report.StepStatus;

public class TestResultListener implements IResultListener, IInvokedMethodListener{
	private static final String BREAK_LINE = "</br>";
	private CoreReporter reporter=ParkarReporter.getInstance();
	

	@Override
	public void onTestSkipped(ITestResult iTestResult) {		
		reporter.startTest(iTestResult.getName(), "SKIP");
		 reporter.reportStep(StepStatus.SKIP, "Finish test case ["+iTestResult.getTestClass().getRealClass().getSimpleName()+"." + iTestResult.getName() + "] with Skip!");
		reporter.endTest();
	}
	
	@Override
	public void onTestFailure(ITestResult iTestResult) {
		reporter.reportStep(StepStatus.FAIL, "Finish test case ["+iTestResult.getTestClass().getRealClass().getSimpleName()+"." + iTestResult.getName() +"] with failure", iTestResult.getThrowable());
		reporter.endTest();
	}
	
	/***
	  * Invoked each time a test fails.
	  * @param iTestResult
	  *          :ITestResult
	  */
	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		reporter.reportStep(StepStatus.PASS, "Finish test case ["+iTestResult.getTestClass().getRealClass().getSimpleName()+"." + iTestResult.getName() +"] with success");
		reporter.endTest();
	}
	
	
	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod()) {
			ParkarSoftAssertion assertion = (ParkarSoftAssertion)testResult.getAttribute("assertion");
			if(assertion != null){
				if (assertion.getVerificationFailures().size() > 0 && testResult.getStatus() == ITestResult.SUCCESS) {
					// set the SUCCESS test to FAILURE
					testResult.setStatus(ITestResult.FAILURE);
				}
				
				int size = assertion.getVerificationFailures().size();
                // if there's only one failure just set that
                if (size == 1) {
                	testResult.setThrowable((Throwable) assertion.getVerificationFailures().get(0));
                } else if(size!=0) {
                    // create a failure message with all failures and stack
                    // traces (except last failure)
                    StringBuffer failureMessage = new StringBuffer("Multiple validation failures (").append(size).append("):" + BREAK_LINE);
                    // set merged throwable
                    Throwable merged = new Throwable(failureMessage.toString());
                    testResult.setThrowable(merged);
                }
			}
		}
	}
	
	/**
	 * If retry is triggered, the test case will be considered as skip, but this skip test actually count in the total test case
	 * For example, 2 test cases are executed, one failed and got retried for 3 times, and at the end the total run will be 5 test cases 
	 * but in reality, we only run 2 test cases.
	 * This code will remove the retry from the total run count.
	 */
	@Override
	public void onFinish(ITestContext context) {
		Set<ITestResult> failedResultSet = context.getFailedTests().getAllResults();
		for (ITestResult failedResult : failedResultSet) {
            ITestNGMethod failedMethod = failedResult.getMethod();
            if (context.getFailedTests().getResults(failedMethod).size() > 1) {
                failedResultSet.remove(failedResult);
            }
            if (context.getPassedTests().getResults(failedMethod).size() > 0) {
                failedResultSet.remove(failedResult);
            }
        }
        
        Set<ITestResult> skippedResultSet = context.getSkippedTests().getAllResults();
        for (ITestResult skippedResult : skippedResultSet) {
            ITestNGMethod skippedMethod = skippedResult.getMethod();
            if (context.getSkippedTests().getResults(skippedMethod).size() > 1) {
            	skippedResultSet.remove(skippedResult);
            }
            if (context.getPassedTests().getResults(skippedMethod).size() > 0) {
            	skippedResultSet.remove(skippedResult);
            }
            if (context.getFailedTests().getResults(skippedMethod).size() > 0) {
            	skippedResultSet.remove(skippedResult);
            }
        }
	}
	
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult iTestResult) {
		
	}
	
	@Override
	public void onTestStart(ITestResult iTestResult) {
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		
	}

	@Override
	public void onStart(ITestContext context) {
		
	}

	

	@Override
	public void onConfigurationSuccess(ITestResult iTestResult) {
		
	}

	@Override
	public void onConfigurationFailure(ITestResult iTestResult) {
		
	}

	@Override
	public void onConfigurationSkip(ITestResult iTestResult) {
		
	}
}
