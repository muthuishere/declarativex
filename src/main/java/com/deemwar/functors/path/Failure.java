package com.deemwar.functors.path;

import com.deemwar.functors.Try;
import com.deemwar.functors.core.TryBlock;
import com.deemwar.functors.interfaces.SupplierWithException;

import java.util.function.Consumer;

public class Failure<T> extends TryBlock<T> {

    Throwable exception;

    public Failure(Throwable value) {
        super(null);
        this.exception = value;


    }

    @Override
    public TryBlock<T> peekError(Consumer<Throwable> s) {
        s.accept(exception);
        return this;
    }


    public <M extends Throwable, L, E extends Throwable> TryBlock<T> onError(Class<M> currentException, SupplierWithException<T, E> s) {
        return exception.getClass().equals(currentException) ? or(s) : this;
    }
    public <L, U extends Throwable> TryBlock<L> or(SupplierWithException<L, U> method) {

        return Try.from(method);
    }
    public TryBlock<T> withErrorLog(String s) {

        return this;
    }

    public TryBlock<T> withErrorLog() {
        return this;
    }

}
