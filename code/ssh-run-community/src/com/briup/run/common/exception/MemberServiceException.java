package com.briup.run.common.exception;

public class MemberServiceException extends Exception {

	private static final long serialVersionUID = -6166533554682206492L;

	public MemberServiceException() {
		super();
	}

	public MemberServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public MemberServiceException(String message) {
		super(message);
	}

	public MemberServiceException(Throwable cause) {
		super(cause);
	}
}
