package global;

import interfaces.models.IResult;

/**
 * Konkrete Basisklasse für IResult: entweder success(V) oder error(E).
 */
public class Result<V, E> implements IResult<V, E> {
    private final V value;
    private final E error;

    private Result(V value, E error) {
        this.value = value;
        this.error = error;
    }

    public static <V, E> Result<V, E> success(V value) {
        return new Result<>(value, null);
    }

    public static <V, E> Result<V, E> error(E error) {
        return new Result<>(null, error);
    }

    @Override
    public boolean isSuccess() {
        return error == null;
    }

    @Override
    public boolean isError() {
        return error != null;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public E getError() {
        return error;
    }
}
