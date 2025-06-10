package exception;

import interfaces.IBookException;
import interfaces.IValidationException;

public class BookUnavailableException extends RuntimeException implements IBookException , IValidationException{

	public BookUnavailableException(String message) {
		super(message);
	}
	

}
