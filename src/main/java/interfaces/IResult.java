package interfaces;

/**
 * Ein generischer Container, der entweder ein erfolgreiches Result.success(V)
 * oder ein Fehler-Result.error(E) repräsentiert.
 */
public interface IResult<V, E> {

    /** @return true, wenn dieses Result erfolgreich ist (contains V). */
    boolean isSuccess();

    /** @return true, wenn dieses Result ein Fehler ist (contains E). */
    boolean isError();

    /** @return den enthaltenen Wert (null, wenn isError()). */
    V getValue();

    /** @return die Fehlermeldung oder den Fehlerwert (null, wenn isSuccess()). */
    E getError();
}
