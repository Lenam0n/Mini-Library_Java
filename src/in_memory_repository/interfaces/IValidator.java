package in_memory_repository.interfaces;

import in_memory_repository.Result;

/**
 * Ein Functional Interface, das einen Wert vom Typ T validiert
 * und als Result<T,String> zurückgibt:
 *   – Result.success(value), wenn die Validierung erfolgreich ist
 *   – Result.error(errorMsg), wenn sie fehlschlägt
 */
@FunctionalInterface
public interface IValidator<T> {
    /**
     * Validiert den übergebenen Wert.
     * @param value das zu prüfende Objekt
     * @return Result.success(value) oder Result.error(String)
     */
    Result<T, String> validate(T value);
}
