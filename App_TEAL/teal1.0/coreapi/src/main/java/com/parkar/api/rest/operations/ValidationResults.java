package com.parkar.api.rest.operations;

public class ValidationResults {
	private boolean isPass;
	private String failedReason;
	
	/**
	 * Constructor without parameter, initialize isPass to false
	 */
	public ValidationResults() {
		isPass = false;
	}
	
	/**
	 * Constructor, initialize isPass to false, failedReason
	 *@param isPass: boolean
	 *@param failedReason: String
	 */
	public ValidationResults(boolean isPass, String failedReason) {
		this.isPass = isPass;
		this.failedReason = failedReason;
	}
	
	/**
	 * Constructor, initialize isPass to false
	 *@param isPass: boolean
	 */
	public ValidationResults(boolean isPass) {
		this.isPass = isPass;
	}
	
	/**
	 * Get the isPass
	 * @return boolean
	 */
	public boolean isPass() {
		return isPass;
	}
	
	/**
	 * Set the isPass
	 * @param isPass: boolean
	 */
	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}
	
	/**
	 * Get the failed reason
	 * @return failedReaason: String
	 */
	public String getFailedReason() {
		return failedReason;
	}
	
	/**
	 * Set the failed reason
	 * @param failedReason: String
	 */
	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}
	
	/**
	 * Append the reason in new line
	 * @param append: String
	 */
	public void appendReasonInNewLine(String append){
		failedReason.concat("\n").concat(append);
	}
	
	/**
	 * Append the reason
	 * @param append: String
	 */
	public void appendReason(String append){
		failedReason.concat(append);
	}
	
}
