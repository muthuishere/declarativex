package declarativex.interfaces;

@FunctionalInterface
public
interface SupplierWithException<T, E extends Throwable> {
    T get() throws E;
}
