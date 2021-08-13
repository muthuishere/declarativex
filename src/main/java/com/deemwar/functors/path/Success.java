package com.deemwar.functors.path;

import com.deemwar.functors.core.TryBlock;

import java.util.function.Consumer;

public class Success<T> extends TryBlock<T> {

    public Success(T value) {
        super(value);
    }

    @Override
    public TryBlock<T> peek(Consumer<T> s) {
        s.accept(this.getValue());
        return this;
    }

}
