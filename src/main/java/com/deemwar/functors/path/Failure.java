package com.deemwar.functors.path;

import com.deemwar.functors.Try;
import com.deemwar.functors.interfaces.SupplierWithException;

import java.util.function.Consumer;

public class Failure<T> extends Try<T> {

    Throwable exception;

    public Failure(Throwable value) {
        super(null);
        this.exception = value;


    }

    @Override
    public Try<T> peekError(Consumer<Throwable> s) {
        s.accept(exception);
        return this;
    }


    public <M extends Throwable, L, E extends Throwable> Try<T> onError(Class<M> currentException, SupplierWithException<T, E> s) {
        return exception.getClass().equals(currentException) ? or(s) : this;
    }
    public <L, U extends Throwable> Try<L> or(SupplierWithException<L, U> method) {

        return Try.from(method);
    }

}
