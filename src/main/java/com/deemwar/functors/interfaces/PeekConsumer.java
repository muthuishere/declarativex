package com.deemwar.functors.interfaces;

@FunctionalInterface
public interface PeekConsumer<T> {
    void accept(T t);
}
