package com.parkar.report;

import java.io.File;
import java.util.Map;

import com.parkar.exception.ParkarCoreCommonException;

public interface CoreReporter extends IReporter {
	
	public void initializeReport(Map<String, String> params) throws ParkarCoreCommonException;
	
	public void addSystemInfo(String key, String value);
	
	public void startTest(String method_name, String method_desc, String... categories);
	
	public void endTest();	
		
	public void deepReportStep(StepStatus status, String stepName) ;
	
	public void deepReportStep(StepStatus status, String stepName,  Throwable t);
	
	public void deepReportStep(StepStatus status, String stepName, String details);
	
	public void deepReportStep(StepStatus status, String stepName, File file) ;
	
	public void deepReportStep(StepStatus status, String stepName, File file, Throwable t);
	
	public void deepReportStep(StepStatus status, String stepName, String details, File file);
	
	public void endReport();

}
