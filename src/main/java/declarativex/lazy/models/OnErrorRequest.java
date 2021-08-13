package declarativex.lazy.models;

import declarativex.interfaces.SupplierWithException;

public class OnErrorRequest<E extends Throwable, T, M extends Throwable> {
    private final Class<M> currentException;
    private final SupplierWithException<T, E> s;

    public OnErrorRequest(Class<M> currentException, SupplierWithException<T, E> s) {
        this.currentException = currentException;
        this.s = s;
    }

    public Class<M> getCurrentException() {
        return currentException;
    }

    public SupplierWithException<T, E> getS() {
        return s;
    }
}
