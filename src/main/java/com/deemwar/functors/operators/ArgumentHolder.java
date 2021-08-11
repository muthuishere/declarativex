package com.deemwar.functors.operators;

import com.deemwar.functors.Try;
import com.deemwar.functors.interfaces.BiFunctionWithException;
import com.deemwar.functors.interfaces.FunctionWithException;
import com.deemwar.functors.interfaces.TriFunctionWithException;

import java.util.List;

public class ArgumentHolder<I1, I2, I3, O, E extends Throwable> {

    public ArgumentHolder(List<Object> methods) {
        this.methods = methods;
    }

    List<Object> methods;

    public Try<O> with(I1 arg1, I2 arg2, I3 arg3) {
        return methods.stream()
                .map(obj -> (TriFunctionWithException<I1, I2, I3, O, E>) obj)
                .map(ioeFunctionWithException -> Try.from(() -> ioeFunctionWithException.apply(arg1, arg2, arg3)))
                .filter(oTry -> oTry.isSuccess())
                .findFirst()
                .orElse(Try.empty());


    }

    public Try<O> with(I1 arg1, I2 arg2) {
        return methods.stream()
                .map(obj -> (BiFunctionWithException<I1, I2, O, E>) obj)
                .map(ioeFunctionWithException -> Try.from(() -> ioeFunctionWithException.apply(arg1, arg2)))
                .filter(oTry -> oTry.isSuccess())
                .findFirst()
                .orElse(Try.empty());


    }

    public Try<O> with(I1 arg1) {
        return methods.stream()
                .map(obj -> (FunctionWithException<I1, O, E>) obj)
                .map(ioeFunctionWithException -> Try.from(() -> ioeFunctionWithException.apply(arg1)))
                .filter(oTry -> oTry.isSuccess())
                .findFirst()
                .orElse(Try.empty());


    }
}
