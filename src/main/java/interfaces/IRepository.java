// Datei: interfaces/IRepository.java
package interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import global.Result;
import interfaces.IPrinter;
import interfaces.models.IEntity;
import interfaces.services.ErrorService;
import interfaces.utils.Validatable;

/**
 * Universelles Repository-Interface:
 *  – CRUD (save, deleteById, findAll)
 *  – Suche (findBy)
 *  – Built-in-Validation & Success-Printing
 *
 * @param <T>  Entity-Typ, der IEntity<ID> & Validatable<T,?> implementiert.
 * @param <ID> Typ der ID (z. B. String für ISBN, Long für Member-ID).
 */
public interface IRepository<
        T extends IEntity<ID> & Validatable<T, ?>,
        ID
    > {

    // ——————————————————————————————
    //  1) Abstrakte CRUD-Methoden
    // ——————————————————————————————
    void save(T entity);
    void deleteById(ID id);
    List<T> findAll();


    // ——————————————————————————————
    //  2) Suche
    // ——————————————————————————————
    /**
     * Liefert alle Entities, die das Prädikat erfüllen.
     * Im Fehlerfall (z.B. keine Treffer) einfach leere Liste.
     */
    default Result<List<T>, RuntimeException> findBy(Predicate<T> filter) {
        List<T> matches = new ArrayList<>();
        for (T e : findAll()) {
            if (filter.test(e)) {
                matches.add(e);
            }
        }
        return Result.success(matches);
    }


    // ——————————————————————————————
    //  3) Validiertes Speichern
    // ——————————————————————————————
    /**
     * Validiert automatisch (via Validatable.validate()),
     * speichert nur bei Erfolg und druckt Abschluss-Message.
     */
    default void save(T entity, String successMsgTemplate) {
        Result<T, RuntimeException> vr = entity.validate();
        if (vr.isError()) {
            ErrorService.handle(vr.getError());
            return;
        }
        save(entity);
        IPrinter.out(String.format(successMsgTemplate, entity));
    }


    // ——————————————————————————————
    //  4) Validiertes Löschen
    // ——————————————————————————————
    /**
     * 1. Baut aus der ID eine Sample-Entity (z. B. isbn → new Book(isbn,"",""))
     * 2. Validiert das Sample (d.h. Ihre ID-Felder)
     * 3. Sucht das echte Objekt in findAll()
     * 4. Löscht und druckt Erfolgsmeldung
     *
     * @param sampleFactory      Wie man aus einer ID eine Entity baut
     * @param notFoundException  Exception, wenn kein/eindeutiger Datensatz
     * @param successMsgTemplate z. B. "Buch %s erfolgreich gelöscht"
     */
    default void deleteById(
        ID id,
        Function<ID, T> sampleFactory,
        Supplier<? extends RuntimeException> notFoundException,
        String successMsgTemplate
    ) {
        ErrorService.run(() -> {
            // 1. Sample erzeugen & Validieren
            T sample = sampleFactory.apply(id);
            Result<T, RuntimeException> vr = sample.validate();
            if (vr.isError()) throw vr.getError();

            // 2. Echte Entity finden
            T found = null;
            for (T e : findAll()) {
                if (e.getId().equals(id)) {
                    if (found != null) throw notFoundException.get();
                    found = e;
                }
            }
            if (found == null) throw notFoundException.get();

            // 3. Löschen + Erfolgsausgabe
            deleteById(found.getId());
            IPrinter.out(String.format(successMsgTemplate, found.getId()));
        });
    }
}
