package com.model;



/**
 * @author Bushra
 *
 */
public class Message {	

	
	private String errorCode;
	private String errMessage;
	private String errorType;
	private String errFieldName;
	private String errRowNumber;
	private Object[] errParameters;
	
	/**
	 * @param errCode
	 */
	public Message(String errCode){
		this(errCode,"");
	}
	
	/**
	 * @param errCode
	 * @param errDescription
	 */
	public Message(String errCode,String errDescription){
		this(errCode,errDescription,"hhj");
	}
	

	/**
	 * @param errCode
	 * @param errDescription
	 * @param errType
	 */
	public Message(String errCode,String errDescription,String errType){
		this(errCode,errDescription,errType,"");
	}
	
	/**
	 * @param errCode
	 * @param errDescription
	 * @param errType
	 * @param errFieldName
	 */
	public Message(String errCode,String errDescription,String errType,String errFieldName){
		this(errCode,errDescription,errType,errFieldName,"1");
	}
	
	/**
	 * @param errCode
	 * @param errDescription
	 * @param errType
	 * @param errFieldName
	 * @param errRowNum
	 */
	public Message(String errCode,String errDescription,String errType,String errFieldName,String errRowNum){
		this(errCode,errDescription,errType,errFieldName,errRowNum,null);
	}
	
	/**
	 * @param errCode
	 * @param errDescription
	 * @param errType
	 * @param errFieldName
	 * @param errRowNum
	 * @param errParams
	 */
	public Message(String errCode,String errDescription,String errType,String errFieldName,String errRowNum,Object[] errParams){
		this.errorCode=errCode;
		this.errMessage=errDescription;
		this.errorType=errType;
		this.errFieldName=errFieldName;
		this.errRowNumber=errRowNum;
		this.errParameters = errParams;
	}
	
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the errMessage
	 */
	public String getErrMessage() {
		return errMessage;
	}
	/**
	 * @param errMessage the errMessage to set
	 */
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	/**
	 * @return the errorType
	 */
	public String getErrorType() {
		return errorType;
	}
	/**
	 * @param errorType the errorType to set
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	/**
	 * @return the fieldName
	 */
	public String getErrFieldName() {
		return errFieldName;
	}
	/**
	 * @param errFieldName 
	 */
	public void setErrFieldName(String errFieldName) {
		this.errFieldName = errFieldName;
	}
	/**
	 * @return the rowNumber
	 */
	public String getErrRowNumber() {
		return errRowNumber;
	}
	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setErrRowNumber(String rowNumber) {
		this.errRowNumber = rowNumber;
	}

	/**
	 * @return the errParameters
	 */
	public Object[] getErrParameters() {
		return errParameters;
	}

	/**
	 * @param errParameters the errParameters to set
	 */
	public void setErrParameters(Object[] errParameters) {
		this.errParameters = errParameters;
	}
}
