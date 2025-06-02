package in_memory_repository.interfaces;
/**
 * Bietet statische Methoden zum einfachen Ausgeben.
 * Aufruf z.B.:
 *   IPrinter.out("Text");
 *   IPrinter.err("Fehlertext");
 */
public interface IPrinter {
    /**
     * Gibt den übergebenen Text (inkl. Zeilenumbruch) auf System.out aus.
     */
    static void out(String message) {
        System.out.println(message);
    }

    /**
     * Gibt den übergebenen Text (inkl. Zeilenumbruch) auf System.err aus.
     */
    static void err(String message) {
        System.err.println(message);
    }
}