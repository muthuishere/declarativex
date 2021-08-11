package com.deemwar.functors.interfaces;

@FunctionalInterface
public
interface SupplierWithException<T, E extends Throwable> {
    T get() throws E;
}
