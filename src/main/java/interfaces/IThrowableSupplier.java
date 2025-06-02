package interfaces;

/**
 * Ein Supplier, der einen Wert vom Typ T liefert und dabei auch checked Exceptions werfen darf.
 * Eignet sich für ErrorService.execute(...), um z. B. DataLoader.loadBooks(...) oder 
 * libraryService.findBookByIsbn(...) aufzurufen und Exception zentral abzufangen.
 *
 * @param <T> Rückgabetyp des Suppliers
 */
@FunctionalInterface
public interface IThrowableSupplier<T> {    
    RuntimeException getException(T param);
}
