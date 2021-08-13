package declarativex.interfaces;

@FunctionalInterface
public interface PeekConsumer<T> {
    void accept(T t);
}
