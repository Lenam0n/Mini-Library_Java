package interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Erweiterung von ICrudRepository um Such‐Funktionalität und “findOrThrow” in einer Methode.
 */
public interface ISearchableRepository<T extends IEntity<ID>, ID> extends ICrudRepository<T, ID> {
    /**
     * Liefert genau ein Element, das das Predicate erfüllt, oder wirft die vom Supplier erzeugte Exception,
     * falls kein oder mehr als ein Element gefunden wurde.
     *
     * @param filter     Filter‐Predicate auf T
     * @param exSupplier Supplier für die Exception, wenn kein oder Mehrfach‐Treffer
     * @return das exakt gefundene Element
     * @throws RuntimeException vom Supplier erzeugt, wenn Suche nicht eindeutig ist
     */
    default T findBy(Predicate<T> filter, Supplier<? extends RuntimeException> exSupplier) {
        T match = null;
        for (T e : findAll()) {
            if (filter.test(e)) {
                if (match != null) {
                    throw exSupplier.get();
                }
                match = e;
            }
        }
        if (match == null) {
            throw exSupplier.get();
        }
        return match;
    }

    /**
     * Liefert alle Elemente, die das Predicate erfüllen.
     *
     * @param filter Predicate auf T
     * @return Liste aller Treffer (kann leer sein)
     */
    default List<T> findBy(Predicate<T> filter) {
        List<T> all = findAll();
        List<T> results = new ArrayList<>();
        for (T e : all) {
            if (filter.test(e)) {
                results.add(e);
            }
        }
        return results;
    }
}
