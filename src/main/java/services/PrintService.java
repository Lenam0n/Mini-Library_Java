package services;

import java.util.List;
import java.util.Objects;

import global.Result;
import interfaces.IPrinter;
import interfaces.IReportGenerator;

/**
 * Druck-Dienst, der mit Result<String, RuntimeException> arbeitet.
 *
 * – Erfolg: Result.success(text) → Ausgabe auf stdout
 * – Fehler:  Result.error(error)   → Ausgabe auf stderr
 */
public interface PrintService {

    /**
     * Druckt das Result:
     * – bei success: IPrinter.out(value)
     * – bei error:   IPrinter.err(error.getMessage())
     */
    default void print(Result<String, ? extends RuntimeException> result) {
        if (result.isError()) {
            IPrinter.err(result.getError().getMessage());
        } else {
            IPrinter.out(result.getValue());
        }
    }

    /**
     * Druckt eine Liste von Objekten. Gibt title, alle items und eine Leerzeile.
     */
    default void printList(String title, List<?> items) {
        Objects.requireNonNull(title, "title darf nicht null sein");
        Objects.requireNonNull(items, "items darf nicht null sein");

        print(Result.success(title));
        for (Object o : items) {
            if (o != null) {
                print(Result.success(o.toString()));
            } else {
                print(Result.error(new RuntimeException("null")));
            }
        }
        print(Result.success(""));
    }

    /**
     * Druckt ein gefundenes Element oder eine Fehlermeldung.
     */
    default <T> void printFound(T item, String notFoundMessage, Object key) {
        Objects.requireNonNull(notFoundMessage, "notFoundMessage darf nicht null sein");

        if (item != null) {
            print(Result.success(item.toString()));
        } else {
            print(Result.error(new RuntimeException(notFoundMessage + ": " + key)));
        }
        print(Result.success(""));
    }

    /**
     * Druckt einen Report, der von IReportGenerator erstellt wird.
     */
    default <T> void printReport(String title, IReportGenerator<T> generator, List<T> items) {
        Objects.requireNonNull(title, "title darf nicht null sein");
        Objects.requireNonNull(generator, "generator darf nicht null sein");
        Objects.requireNonNull(items, "items darf nicht null sein");

        print(Result.success("=== " + title + " ==="));
        String report = generator.generate(items);
        if (report != null) {
            print(Result.success(report));
        } else {
            print(Result.error(new RuntimeException("Report ist null")));
        }
        print(Result.success(""));
    }

    /**
     * Druckt eine Liste von Objekten: jede Zeile mit „  “ vorangestellt.
     */
    default <T> void printResults(List<T> items) {
        for (T item : items) {
            print(Result.success("  " + item));
        }
    }

    /**
     * Druckt eine Kopfzeile: <label> '<filter>':
     */
    default void printHeader(String label, String filter) {
        print(Result.success(label + " '" + filter + "':"));
    }
    
    /** Überladung nur mit einem kompletten Header-Text (z.B. "=== Ausleihreport ===") */
    default void printHeader(String header) {
        Objects.requireNonNull(header, "header darf nicht null sein");
        print(Result.success(header));
    }

    /**
     * Druckt eine Leerzeile als Footer.
     */
    default void printFooter() {
        print(Result.success(""));
    }
}
