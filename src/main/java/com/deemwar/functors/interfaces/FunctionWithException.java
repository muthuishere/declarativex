package com.deemwar.functors.interfaces;

@FunctionalInterface
public
interface FunctionWithException<I, O, E extends Throwable> {
    O apply(I value) throws E;
}
