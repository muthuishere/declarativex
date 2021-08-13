package declarativex.interfaces;

@FunctionalInterface
public interface PeekErrorConsumer<T extends Throwable> {
    void accept(T t);
}
