package com.deemwar.functors.interfaces;

@FunctionalInterface
public interface PeekErrorConsumer<T extends Throwable> {
    void accept(T t);
}
