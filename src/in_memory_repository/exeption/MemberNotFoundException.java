package in_memory_repository.exeption;

/**
* Spezielle RuntimeException, wenn ein Member nicht gefunden wird.
*/
public class MemberNotFoundException extends RuntimeException {
 public MemberNotFoundException(String message) {
     super(message);
 }
}
