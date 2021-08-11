package com.deemwar.functors.interfaces;

@FunctionalInterface
interface SupplierWithInputException<T, EI, EO extends Throwable> {
    T get(EI exception) throws EO;
}
