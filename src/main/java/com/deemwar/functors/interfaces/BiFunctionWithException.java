package com.deemwar.functors.interfaces;

@FunctionalInterface
public
interface BiFunctionWithException<I1, I2, O, E extends Throwable> {
    O apply(I1 value, I2 value2) throws E;
}
