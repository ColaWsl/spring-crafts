package com.crafts.spring.exception;

public class NoExistsBeanException extends Exception{
	private static final String DEFAULT_MESSAGE = "没有这个Bean被定义:";
	private static final String DEFAULT_MESSAGE_EN = "No Bean is defined:";

	public NoExistsBeanException(String message) {
		super(DEFAULT_MESSAGE_EN + message);
	}
}
