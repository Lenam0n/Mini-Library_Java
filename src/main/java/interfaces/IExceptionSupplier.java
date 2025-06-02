package interfaces;

/**
 * Ein einfaches Functional Interface, das aus einem Schlüssel-Wert
 * (z. B. ISBN oder Member-ID) eine RuntimeException erzeugt.
 *
 * Beispiel-Aufruf in LibraryService:
 *   () -> bookNotFoundEx.create(isbn)
 *
 * @param <I> der Typ des Schlüssels (z. B. String für ISBN oder Long für Member-ID)
 */
@FunctionalInterface
public interface IExceptionSupplier<I> {
    /**
     * Erzeuge eine RuntimeException auf Basis des übergebenen Schlüssels.
     *
     * @param id der Schlüssel (z. B. ISBN oder Member-ID), anhand dessen
     *           die Fehlermeldung/Exception aufgebaut wird
     * @return eine passende RuntimeException
     */
    RuntimeException create(I id);
}
