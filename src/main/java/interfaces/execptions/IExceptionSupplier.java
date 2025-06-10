package interfaces.execptions;

/**
 * Funktionales Interface, das aus einem Eingabeparameter vom Typ T
 * eine RuntimeException erzeugt. In findBy(...) wird dann eine
 * Supplier<RuntimeException> daraus gebaut.
 *
 * @param <T> Typ des Eingabeparameters (z.B. String für ISBN, Long für Member-ID)
 */
@FunctionalInterface
public interface IExceptionSupplier<T> {
    /**
     * Liefert einen Wert vom Typ T oder wirft eine Exception.
     * 
     * @return  einen Wert vom Typ T (z. B. List<Book>, Book, Member)
     * @throws Exception  jede beliebige Exception (checked oder unchecked)
     */
    T get() throws Exception;
}
