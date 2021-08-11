package com.deemwar.functors;

import com.deemwar.functors.interfaces.*;
import com.deemwar.functors.operators.ArgumentHolder;
import com.deemwar.functors.path.Failure;
import com.deemwar.functors.path.Success;


import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;


public class Try<T> {

    final T value;
    private static final Logger log = Logger.getLogger(Try.class.getName());

    public Try(T value) {
        this.value = value;
    }

    public static <T> Try<T> empty() {
        return new Try<>(null);
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


    public Try<T> withErrorLog(String s) {
        log.warning(s);
        return this;
    }


    public static <L, U extends Throwable> Try<L> from(SupplierWithException<L, U> method) {

        try {
            return new Success<L>(method.get());

        } catch (Throwable e) {
            return new Failure<L>(e);
        }
    }

    public <L, U extends Throwable> Try<L> or(SupplierWithException<L, U> method) {
            return (Try<L>) this;
    }

    public T getValue() {
        return value;
    }


    public static <I1, I2, I3, O, E extends Throwable> ArgumentHolder<I1, I2, I3, O, E> any(TriFunctionWithException<I1, I2, I3, O, E>... methods) {

        return new ArgumentHolder(Arrays.asList(methods));

    }

    public static <I1, I2, I3, O, E extends Throwable> ArgumentHolder<I1, I2, I3, O, E> any(BiFunctionWithException<I1, I3, O, E>... methods) {

        return new ArgumentHolder(Arrays.asList(methods));

    }

    public static <I1, I2, I3, O, E extends Throwable> ArgumentHolder<I1, I2, I3, O, E> any(FunctionWithException<I1, O, E>... methods) {

        return new ArgumentHolder(Arrays.asList(methods));

    }


    public T orElseGet(T o) {
        return isSuccess() ? value : o;

    }

    public T get() {
        return isSuccess() ? value : null;

    }

    public Try<T> peekError(Consumer<Throwable> s) {
        return this;
    }

    public <M extends Throwable, L, E extends Throwable> Try<T> onError(Class<M> currentException, SupplierWithException<T, E> s) {
        return this;
    }


    public Try<T> peek(Consumer<T> s) {
        return this;
    }
}
