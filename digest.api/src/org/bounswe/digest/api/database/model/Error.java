package org.bounswe.digest.api.database.model;

public class Error extends Model{
	private String errorName;
	private String errorDescription;
	public Error(String errorName, String errorDescription){
		this.errorName = errorName;
		this.errorDescription = errorDescription;
		
	}
	public String getErrorName() {
		return errorName;
	}
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	
}
