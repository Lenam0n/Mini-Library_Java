package interfaces;

import exception.BookNotFoundException;
import exception.MemberNotFoundException;

/**
* Statt eigener „ExceptionSuppliers“-Klasse packen wir die beiden Lambdas
* direkt in ein Interface als public‐static‐final Konstanten.
*/
public interface INotFoundExceptions {
 IThrowableSupplier<String> BOOK_NOT_FOUND = isbn ->
     new BookNotFoundException("Buch mit ISBN " + isbn + " nicht gefunden");

 IThrowableSupplier<Long> MEMBER_NOT_FOUND = id ->
     new MemberNotFoundException("Mitglied mit ID " + id + " nicht gefunden");
}
