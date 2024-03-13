package cn.exam.util;

/**
 * 返回的结果信息
 * @Descrption
 */
public class Result {

	/**
	 * 错误码
	 */
	private String errCode;
	/**
	 * 错误信息
	 */
	private String errMsg;
	/**
	 * 错误结果
	 */
	private String result;

	public Result(){}

	public Result(String errCode, String errMsg){
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public Result(String errCode, String errMsg, String result){
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.result = result;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
