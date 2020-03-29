package in.quallit.junitwithmockito.exception;

public class CustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8864763830715041791L;
	private String code;

	public CustomException() {
		super();
	}

	public CustomException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
