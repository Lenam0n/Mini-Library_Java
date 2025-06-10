package exceptions;

/**
* Wird geworfen, wenn das Repository–Initialisieren in Main scheitert.
*/
public class RepositoryInitializationException extends RuntimeException {
 public RepositoryInitializationException(String message) {
     super(message);
 }
}
