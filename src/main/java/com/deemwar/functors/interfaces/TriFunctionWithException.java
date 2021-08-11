package com.deemwar.functors.interfaces;

@FunctionalInterface
public interface TriFunctionWithException<I1, I2, I3, O, E extends Throwable> {
   public O apply(I1 value, I2 value2, I3 value3) throws E;
}
