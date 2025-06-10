// Datei: exception/ItemsNullException.java
package exception;

import java.util.List;

/**
 * Wird geworfen, wenn die übergebene Liste null ist.
 */
public class ItemsNullException extends RuntimeException {
    public ItemsNullException(String context) {
        super("Liste '" + context + "' darf nicht null sein");
    }
}
