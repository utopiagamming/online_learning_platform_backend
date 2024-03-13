package cn.exam.util;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Component
@ControllerAdvice
@NoArgsConstructor
public class ExpressException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 8488113818471141915L;
	public String errMsg;
	public String errCode;

	public ExpressException(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}


	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
}
