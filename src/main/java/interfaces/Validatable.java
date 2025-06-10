// ---------------------
// Datei: interfaces/Validatable.java
// ---------------------
package interfaces;

import global.Result;

/**
 * Entities, die Validierung unterstützen, implementieren dieses Interface.
 *
 * @param <T> Typ der Entity
 * @param <V> Typ des Validators für T (muss IValidator<T> implementieren)
 */
public interface Validatable<T, V extends IValidator<T>> {
    /**
     * Liefert die zugeordnete Validator-Instanz (vom Typ V).
     */
    V getValidator();

    /**
     * Führt die Validierung über den zugeordneten Validator aus.
     *
     * @return Result.success(this) oder Result.error(e)
     */
    @SuppressWarnings("unchecked")
    default Result<T, RuntimeException> validate() {
        return getValidator().validateEntity((T) this);
    }
}
