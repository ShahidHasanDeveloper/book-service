package com.epam.books.exceptions;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler{


	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		CustomErrorDetails customErrors= new CustomErrorDetails(new Date(), "Method Argument  is not valid ", ex.getMessage());
		return new ResponseEntity<Object>(customErrors, HttpStatus.BAD_REQUEST);
	}


	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
			HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {


		CustomErrorDetails customErrors= new CustomErrorDetails(new Date(), "Http Media type is not supported ", ex.getMessage());
		return new ResponseEntity<Object>(customErrors, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {


		CustomErrorDetails customErrors= new CustomErrorDetails(new Date(), "Http Method is not supported ", ex.getMessage());
		return new ResponseEntity<Object>(customErrors, HttpStatus.METHOD_NOT_ALLOWED);
	}


	@ExceptionHandler(value = BookExistsException.class)
	public final ResponseEntity<Object>handleBookExitsException(
			BookExistsException ex, WebRequest request){
		
		CustomErrorDetails customErrors= new CustomErrorDetails(new Date(), "Book is already present ", ex.getMessage());
		return new ResponseEntity<Object>(customErrors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> hanldeConstraintViolationException(ConstraintViolationException ex, WebRequest request){
		
		CustomErrorDetails customErrors= new CustomErrorDetails(new Date(), "Constraint is violated", ex.getMessage());
		return new ResponseEntity<Object>(customErrors, HttpStatus.BAD_REQUEST);
	}
	
}
