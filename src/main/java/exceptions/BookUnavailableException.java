package exceptions;

import interfaces.execptions.IBookException;
import interfaces.execptions.IValidationException;

public class BookUnavailableException extends RuntimeException implements IBookException , IValidationException{

	public BookUnavailableException(String message) {
		super(message);
	}
	

}
