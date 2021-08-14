package declarativex.lazy;

import declarativex.lazy.models.OnErrorRequest;
import declarativex.core.TryBlock;
import declarativex.interfaces.SupplierWithException;
import declarativex.lazy.models.LazyRequest;
import declarativex.lazy.models.Operation;
import declarativex.path.Failure;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.logging.Logger;


public class TryLazyBlock<T> {


    private static final Logger log = Logger.getLogger(TryLazyBlock.class.getName());

    List<LazyRequest> ops = new ArrayList<>();
    Map<Operation, BiFunction<TryBlock,LazyRequest, TryBlock>> rules = new HashMap<>();

    public TryLazyBlock() {

        rules.put(Operation.SUPPLIER,this::onExecuteSupplier);
        rules.put(Operation.PEEK,this::onPeek);
        rules.put(Operation.PEEKERROR,this::onPeekError);
        rules.put(Operation.ONERROR,this::on);

    }

    public static <T> TryLazyBlock<T> empty() {
        return new TryLazyBlock<>();
    }


    public Optional<T> toOptional() {
        return Optional.ofNullable(get());
    }



    private void put(Operation operation, Object method) {
        this.ops.add(new LazyRequest(operation, method));
    }

    public <L, U extends Throwable> TryLazyBlock<L> or(SupplierWithException<L, U> method) {
        Objects.requireNonNull(method);
        put(Operation.SUPPLIER, method);
        return (TryLazyBlock<L>) this;
    }


    public T orElseGet(T o) {
        T value = get();
        return null != value ? value : o;

    }


    public <L, U extends Throwable, E extends Throwable> T get() {

        TryBlock<T> lazyResult = new Failure<>(null);
        for (LazyRequest entry : ops) {
            lazyResult = rules.get(entry.getOperation()).apply(lazyResult,entry);
        }
        return lazyResult.getValue();


    }

    public <U extends Throwable, E extends Throwable> TryBlock<T> on(TryBlock<T> lazyResult, LazyRequest entry) {
        OnErrorRequest<E, T, U> consumer = (OnErrorRequest<E, T, U>) entry.getValue();
        lazyResult = lazyResult.on(consumer.getCurrentException(), consumer.getS());
        return lazyResult;
    }

    public TryBlock<T> onPeekError(TryBlock<T> lazyResult, LazyRequest entry) {
        Consumer<Throwable> consumer = (Consumer<Throwable>) entry.getValue();
        lazyResult = lazyResult.peekError(consumer);
        return lazyResult;
    }





    TryBlock<T> onPeek(TryBlock<T> lazyResult, LazyRequest entry) {
        Consumer<T> consumer = (Consumer<T>) entry.getValue();
        lazyResult = lazyResult.peek(consumer);
        return lazyResult;
    }


    public  <U extends Throwable> TryBlock<T> onExecuteSupplier(TryBlock<T> lazyResult, LazyRequest entry) {
        if (lazyResult.isFailure()) {
            SupplierWithException<T, U> method = (SupplierWithException<T, U>) entry.getValue();
            lazyResult = lazyResult.or(method);
        }
        return lazyResult;
    }

    public TryLazyBlock<T> peekError(Consumer<Throwable> s) {

        put(Operation.PEEKERROR, s);
        return this;
    }

    public <M extends Throwable, L, E extends Throwable> TryLazyBlock<T> on(Class<M> currentException, SupplierWithException<T, E> s) {
        return on(new OnErrorRequest<>(currentException, s));
    }

    public <M extends Throwable, L, E extends Throwable> TryLazyBlock<T> on(OnErrorRequest<E, T, M> onErrorRequest) {

        put(Operation.ONERROR, onErrorRequest);
        return this;
    }


    public TryLazyBlock<T> peek(Consumer<T> s) {

        put(Operation.PEEK, s);
        return this;
    }


}
