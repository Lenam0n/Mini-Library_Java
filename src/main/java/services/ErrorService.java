package services;

import interfaces.IPrinter;
import interfaces.execptions.IExceptionSupplier;
import interfaces.execptions.IRunnableException;
import interfaces.execptions.IThrowableSupplier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import exception.BookAuthorEmptyException;
import exception.BookIsbnInvalidException;
import exception.BookNotFoundException;
import exception.BookNullException;
import exception.BookTitleEmptyException;
import exception.BookUnavailableException;
import exception.BorrowNotAllowedException;
import exception.GeneratorNullException;
import exception.ItemsNullException;
import exception.LoanDateInFutureException;
import exception.LoanDateNullException;
import exception.LoanIsbnEmptyException;
import exception.LoanMemberIdNullException;
import exception.LoanNullException;
import exception.MemberEmailInvalidException;
import exception.MemberNameEmptyException;
import exception.MemberNotFoundException;
import exception.MemberNullException;
import exception.NotFoundMessageNullException;
import exception.ReportGeneratorMissingException;
import exception.ReportItemsNullException;
import exception.ReportNullException;
import exception.RepositoryInitializationException;
import exception.ReturnNotAllowedException;
import exception.TitleNullException;

/**
 * Zentraler ErrorService:
 * - execute(...) fängt alle Exceptions aus IExceptionSupplier ab.
 * - run(...) fängt alle Exceptions aus IRunnableException ab.
 * - throwException(...) erzeugt per IThrowableSupplier eine RuntimeException
 *   und leitet sie an den passenden Handler weiter.
 *
 * Hier werden nun alle benutzerdefinierten Book-, Member-, Loan-, Print-,
 * Reporting- und weitere Exceptions registriert.
 */
public class ErrorService {
    private static final Map<Class<? extends Exception>, Consumer<Exception>> HANDLERS = new HashMap<>();

    static {
        // --- Book‐Exceptions ---
        HANDLERS.put(BookNullException.class, ex ->
            IPrinter.err("Buch‐Fehler: Objekt darf nicht null sein")
        );
        HANDLERS.put(BookTitleEmptyException.class, ex ->
            IPrinter.err("Buch‐Fehler: Titel darf nicht leer sein")
        );
        HANDLERS.put(BookAuthorEmptyException.class, ex ->
            IPrinter.err("Buch‐Fehler: Autor darf nicht leer sein")
        );
        HANDLERS.put(BookIsbnInvalidException.class, ex ->
            IPrinter.err("Buch‐Fehler: Ungültige ISBN")
        );
        HANDLERS.put(BookNotFoundException.class, ex ->
            IPrinter.err("Buch nicht gefunden: " + ex.getMessage())
        );
        HANDLERS.put(BookUnavailableException.class, ex ->
            IPrinter.err("Ausleih‐Fehler: " + ex.getMessage())
        );

        // --- Member‐Exceptions ---
        HANDLERS.put(MemberNullException.class, ex ->
            IPrinter.err("Mitglieder‐Fehler: Objekt darf nicht null sein")
        );
        HANDLERS.put(MemberNameEmptyException.class, ex ->
            IPrinter.err("Mitglieder‐Fehler: Name darf nicht leer sein")
        );
        HANDLERS.put(MemberEmailInvalidException.class, ex ->
            IPrinter.err("Mitglieder‐Fehler: Ungültige E-Mail")
        );
        HANDLERS.put(MemberNotFoundException.class, ex ->
            IPrinter.err("Mitglied nicht gefunden: " + ex.getMessage())
        );

        // --- Loan‐Exceptions ---
        HANDLERS.put(LoanNullException.class, ex ->
            IPrinter.err("Ausleih‐Fehler: Objekt darf nicht null sein")
        );
        HANDLERS.put(LoanIsbnEmptyException.class, ex ->
            IPrinter.err("Ausleih‐Fehler: ISBN darf nicht leer sein")
        );
        HANDLERS.put(LoanMemberIdNullException.class, ex ->
            IPrinter.err("Ausleih‐Fehler: MemberID darf nicht null sein")
        );
        HANDLERS.put(LoanDateNullException.class, ex ->
            IPrinter.err("Ausleih‐Fehler: Datum darf nicht null sein")
        );
        HANDLERS.put(LoanDateInFutureException.class, ex ->
            IPrinter.err("Ausleih‐Fehler: Datum liegt in der Zukunft")
        );

        // --- Borrow/Return‐Exceptions ---
        HANDLERS.put(BorrowNotAllowedException.class, ex ->
            IPrinter.err(ex.getMessage())
        );
        HANDLERS.put(ReturnNotAllowedException.class, ex ->
            IPrinter.err(ex.getMessage())
        );

        // --- PrintService‐Exceptions (Null-/RequireNonNull-Fälle) ---
        HANDLERS.put(TitleNullException.class, ex ->
            IPrinter.err("Druck‐Fehler: Titel darf nicht null sein")
        );
        HANDLERS.put(ItemsNullException.class, ex ->
            IPrinter.err("Druck‐Fehler: Liste darf nicht null sein")
        );
        HANDLERS.put(NotFoundMessageNullException.class, ex ->
            IPrinter.err("Druck‐Fehler: notFoundMessage darf nicht null sein")
        );

        // --- Report‐Exceptions ---
        HANDLERS.put(GeneratorNullException.class, ex ->
            IPrinter.err("Report‐Fehler: Generator darf nicht null sein")
        );
        HANDLERS.put(ReportItemsNullException.class, ex ->
            IPrinter.err("Report‐Fehler: Item-Liste darf nicht null sein")
        );
        HANDLERS.put(ReportNullException.class, ex ->
            IPrinter.err("Report‐Fehler: Report-String ist null")
        );
        HANDLERS.put(ReportGeneratorMissingException.class, ex ->
            IPrinter.err("Report‐Fehler: Kein Report-Generator verfügbar")
        );

        // --- Repository‐Exceptions ---
        HANDLERS.put(RepositoryInitializationException.class, ex ->
            IPrinter.err("Repository‐Fehler: " + ex.getMessage())
        );

        // --- Fallback für alle anderen Exceptions ---
        HANDLERS.put(Exception.class, ex ->
            IPrinter.err("Unerwarteter Fehler: " + ex.getMessage())
        );
    }

    private ErrorService() { }

    /**
     * Führt den IExceptionSupplier aus, fängt Exceptions ab und leitet sie an den Handler weiter.
     * Gibt null zurück, falls eine Exception aufgetreten ist.
     *
     * @param supplier Block, der einen Wert liefert oder Exception wirft
     * @param <T>      Rückgabetyp
     * @return Ergebnis des Suppliers oder null im Fehlerfall
     */
    public static <T> T execute(IExceptionSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            handle(e);
            return null;
        }
    }

    /**
     * Führt den IRunnableException aus, fängt Exceptions ab und leitet sie an den Handler weiter.
     *
     * @param runnable Block, der keine Rückgabe liefert, aber Exception wirft
     */
    public static void run(IRunnableException runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            handle(e);
        }
    }

    /**
     * Erzeugt per IThrowableSupplier eine RuntimeException aus dem Parameter
     * und leitet sie an den Handler weiter.
     *
     * @param param    Eingabewert (z. B. ISBN oder Member-ID)
     * @param supplier Produziert die passende RuntimeException
     * @param <T>      Typ des Parameters
     */
    public static <T> void throwException(T param, IThrowableSupplier<T> supplier) {
        RuntimeException ex = supplier.getException(param);
        handle(ex);
    }

    public static void handle(Exception e) {
        Consumer<Exception> handler = HANDLERS.get(e.getClass());
        if (handler == null) {
            for (Map.Entry<Class<? extends Exception>, Consumer<Exception>> entry : HANDLERS.entrySet()) {
                if (entry.getKey().isInstance(e)) {
                    handler = entry.getValue();
                    break;
                }
            }
        }
        if (handler != null) {
            handler.accept(e);
        } else {
            HANDLERS.get(Exception.class).accept(e);
        }
    }
}
