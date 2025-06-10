// Datei: interfaces/IValidator.java
package interfaces;

import global.Result;

/**
 * Ein Functional Interface, das einen Wert vom Typ T validiert
 * und als Result<T, RuntimeException> zurückgibt.
 */
@FunctionalInterface
public interface IValidator<T> {
    /**
     * Validiert den übergebenen Wert.
     *
     * @param value das zu prüfende Objekt
     * @return Result.success(value) oder Result.error(e)
     */
    Result<T, RuntimeException> validateEntity(T value);
}
