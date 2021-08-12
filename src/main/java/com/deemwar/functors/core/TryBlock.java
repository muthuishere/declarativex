package com.deemwar.functors.core;

import com.deemwar.functors.interfaces.*;


import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;


public class TryBlock<T> {

    final T value;
    private static final Logger log = Logger.getLogger(TryBlock.class.getName());
    public TryBlock(T value) {
        this.value = value;
    }

    public static <T> TryBlock<T> empty() {
        return new TryBlock<>(null);
    }

    public boolean isSuccess() {
        return null != value;
    }

    public boolean isFailure() {
        return null == value;
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(value);
    }


    public TryBlock<T> withErrorLog(String s) {
        return this;
    }



    public <L, U extends Throwable> TryBlock<L> or(SupplierWithException<L, U> method) {
        Objects.requireNonNull(method);
            return (TryBlock<L>) this;
    }

    public T getValue() {
        return value;
    }




    public T orElseGet(T o) {
        return isSuccess() ? value : o;

    }

    public T get() {
        return isSuccess() ? value : null;

    }

    public TryBlock<T> peekError(Consumer<Throwable> s) {
        return this;
    }

    public <M extends Throwable, L, E extends Throwable> TryBlock<T> onError(Class<M> currentException, SupplierWithException<T, E> s) {
        return this;
    }


    public TryBlock<T> peek(Consumer<T> s) {
        return this;
    }
}
