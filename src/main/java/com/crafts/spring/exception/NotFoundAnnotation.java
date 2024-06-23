package com.crafts.spring.exception;

public class NotFoundAnnotation extends RuntimeException{

	private static final String DEFAULT_MESSAGE = "没有找到注解:";
	private static final String DEFAULT_MESSAGE_EN = "No annotation found:";

	public NotFoundAnnotation(String message) {

		super(DEFAULT_MESSAGE_EN + message);
	}
}
