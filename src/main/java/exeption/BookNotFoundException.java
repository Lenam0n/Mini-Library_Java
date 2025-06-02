package exeption;

/**
* Spezielle RuntimeException, wenn ein Buch nicht gefunden wird.
*/
public class BookNotFoundException extends RuntimeException {
 public BookNotFoundException(String message) {
     super(message);
 }
}
