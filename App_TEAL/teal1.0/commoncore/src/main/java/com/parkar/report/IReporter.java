package com.parkar.report;

import java.io.File;

public interface IReporter
{	
	public void reportStep(String stepName) ;

	public void reportStep(StepStatus status, String stepName) ;

	public void reportStep(StepStatus status, String stepName,  Throwable t);

	public void reportStep(StepStatus status, String stepName, String details);

	public void reportStep(StepStatus status, String stepName, File file) ;

	public void reportStep(StepStatus status, String stepName, File file, Throwable t);

	public void reportStep(StepStatus status, String stepName, String details, File file);
}