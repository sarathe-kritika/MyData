package com.parkar.assertion;

import java.util.ArrayList;
import java.util.List;

import com.parkar.exception.ParkarCoreCommonException;

public abstract class ParkarSoftAssertion {

	protected static final String BREAK_LINE = "</br>";
    protected List<Throwable> verificationFailures = new ArrayList<Throwable>();

	public List<Throwable> getVerificationFailures() {
		return verificationFailures;
	}
	
	/**
	 * To have this statement in the last line of the test case. user will enable retry option in the framework
	 * Without having this method called, no retry will be performed.
	 * 
	 * @throws ParkarCoreCommonException throw a ParkarCoreCommonException
	 * 		
	 */
	public void verifyAll() throws ParkarCoreCommonException{
		if (this.getVerificationFailures().size() > 0) {
			int size = this.getVerificationFailures().size();
            // if there's only one failure just set that
            if (size == 1) {
            	throw new ParkarCoreCommonException(((Throwable) this.getVerificationFailures().get(0)).getMessage());
            } else if(size!=0) {
                // create a failure message with all failures and stack
                // traces (except last failure)
                StringBuffer failureMessage = new StringBuffer("Multiple validation failures (").append(size).append("):"+BREAK_LINE);
                // set merged throwable
                Throwable merged = new Throwable(failureMessage.toString());

                throw new ParkarCoreCommonException(merged.getMessage());
            }
		}
	}

}

