package interfaces;

import interfaces.IPrinter;
import interfaces.services.ErrorService;

/**
 * Vereinheitlicht:
 * - parameterlose Aktionen
 * - Save-Operationen mit Entity
 *
 * Abstrakte Methode:
 *   void execute(T input) throws Exception;
 *
 * Statische Helfer:
 *   run(...): für Aktionen ohne Parameter
 *   save(...): für Speichern mit Erfolgsmeldung
 */
@FunctionalInterface
public interface IActions<T> {

    void execute(T input) throws Exception;

    /**
     * Führt eine parameterlose Aktion via ErrorService aus.
     *
     * Beispiel:
     *   IAction.run(v -> someThrowingMethod());
     *
     * @param action Lambda, dessen execute(null) läuft
     */
    static void run(IActions<Void> action) {
        ErrorService.run(() -> action.execute(null));
    }

    /**
     * Führt eine Save-Operation via ErrorService aus
     * und gibt danach "Erfolgreich gespeichert: {entity}" aus.
     *
     * Beispiel:
     *   IAction.save(book, b -> bookRepo.save(b));
     *   // oder mit Methodenreferenz:
     *   IAction.save(book, bookRepo::save);
     *
     * @param entity zu speicherndes Objekt
     * @param action Lambda, dessen execute(entity) speichert
     * @param <T> Typ der Entität
     */
    static <T> void save(T entity, IActions<T> action) {
        ErrorService.run(() -> {
            action.execute(entity);
            IPrinter.out("Erfolgreich gespeichert: " + entity);
        });
    }
}
