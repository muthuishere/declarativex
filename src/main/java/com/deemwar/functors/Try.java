package com.deemwar.functors;

import com.deemwar.functors.core.TryBlock;
import com.deemwar.functors.interfaces.BiFunctionWithException;
import com.deemwar.functors.interfaces.FunctionWithException;
import com.deemwar.functors.interfaces.SupplierWithException;
import com.deemwar.functors.interfaces.TriFunctionWithException;
import com.deemwar.functors.lazy.LazyBuilder;

import com.deemwar.functors.path.Failure;
import com.deemwar.functors.path.Success;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Try<I1, I2, I3, O, E extends Throwable> {


    public final static LazyBuilder<?,?,?,?,?> lazy = new LazyBuilder();
    public Try(List<Object> methods) {
        this.methods = methods;
    }



    List<Object> methods;

    public static <L, U extends Throwable> TryBlock<L> from(SupplierWithException<L, U> method) {
        Objects.requireNonNull(method);
        try {
            return new Success<L>(method.get());

        } catch (Throwable e) {
            return new Failure<L>(e);
        }
    }

    public TryBlock<O> with(I1 arg1, I2 arg2, I3 arg3) {
        return methods.stream()
                .map(obj -> (TriFunctionWithException<I1, I2, I3, O, E>) obj)
                .map(ioeFunctionWithException -> from(() -> ioeFunctionWithException.apply(arg1, arg2, arg3)))
                .filter(oTry -> oTry.isSuccess())
                .findFirst()
                .orElse(TryBlock.empty());


    }

    public TryBlock<O> with(I1 arg1, I2 arg2) {
        return methods.stream()
                .map(obj -> (BiFunctionWithException<I1, I2, O, E>) obj)
                .map(ioeFunctionWithException -> from(() -> ioeFunctionWithException.apply(arg1, arg2)))
                .filter(oTry -> oTry.isSuccess())
                .findFirst()
                .orElse(TryBlock.empty());


    }

    public TryBlock<O> with(I1 arg1) {
        return methods.stream()
                .map(obj -> (FunctionWithException<I1, O, E>) obj)
                .map(ioeFunctionWithException -> from(() -> ioeFunctionWithException.apply(arg1)))
                .filter(oTry -> oTry.isSuccess())
                .findFirst()
                .orElse(TryBlock.empty());


    }

    public static <I1, I2, I3, O, E extends Throwable> Try<I1, I2, I3, O, E> any(TriFunctionWithException<I1, I2, I3, O, E>... methods) {

        return new Try(Arrays.asList(methods));

    }

    public static <I1, I2, I3, O, E extends Throwable> Try<I1, I2, I3, O, E> any(BiFunctionWithException<I1, I3, O, E>... methods) {

        return new Try(Arrays.asList(methods));

    }

    public static <I1, I2, I3, O, E extends Throwable> Try<I1, I2, I3, O, E> any(FunctionWithException<I1, O, E>... methods) {

        return new Try(Arrays.asList(methods));

    }
}
